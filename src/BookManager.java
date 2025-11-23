import java.sql.*;
import java.util.List;
import java.util.Scanner;
import model.Book;
import model.Visitor;
import model.VisitorData;

public class BookManager {
    private Connection connection;
    private Scanner scanner;

    public BookManager(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
        createTables();
    }

    // Создание таблиц для книг и посетителей
    private void createTables() {
        String createVisitorsTable = "CREATE TABLE IF NOT EXISTS visitors (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "surname TEXT NOT NULL, " +
                "phone TEXT UNIQUE NOT NULL, " +
                "subscribed BOOLEAN, " +
                "UNIQUE(name, surname))";

        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "publishing_year INTEGER, " +
                "isbn TEXT UNIQUE, " +
                "publisher TEXT, " +
                "visitor_name TEXT, " +
                "FOREIGN KEY (visitor_name) REFERENCES visitors(name))";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createVisitorsTable);
            stmt.execute(createBooksTable);
            System.out.println("Таблицы для книг и посетителей созданы/проверены.");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблиц: " + e.getMessage());
        }
    }

    // 4. Добавление данных из JSON в базу (только уникальные)
    public void importDataFromJSON(List<VisitorData> visitorsData) {
        if (visitorsData == null || visitorsData.isEmpty()) {
            System.out.println("Нет данных для импорта");
            return;
        }

        int totalVisitors = 0;
        int totalBooks = 0;

        for (VisitorData visitorData : visitorsData) {
            // Добавляем посетителя (только если уникальный)
            if (addVisitor(visitorData)) {
                totalVisitors++;
            }

            // Добавляем книги посетителя
            for (Book book : visitorData.getFavoriteBooks()) {
                if (addBook(book, visitorData.getName())) {
                    totalBooks++;
                }
            }
        }

        System.out.printf("Импорт завершен. Добавлено: %d посетителей, %d книг%n", totalVisitors, totalBooks);
    }

    private boolean addVisitor(VisitorData visitorData) {
        String sql = "INSERT OR IGNORE INTO visitors (name, surname, phone, subscribed) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, visitorData.getName());
            pstmt.setString(2, visitorData.getSurname());
            pstmt.setString(3, visitorData.getPhone());
            pstmt.setBoolean(4, visitorData.isSubscribed());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка добавления посетителя " + visitorData.getName() + ": " + e.getMessage());
            return false;
        }
    }

    private boolean addBook(Book book, String visitorName) {
        String sql = "INSERT OR IGNORE INTO books (name, author, publishing_year, isbn, publisher, visitor_name) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getPublishingYear());
            pstmt.setString(4, book.getIsbn());
            pstmt.setString(5, book.getPublisher());
            pstmt.setString(6, visitorName);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка добавления книги '" + book.getName() + "': " + e.getMessage());
            return false;
        }
    }

    // 5. Отсортированный список книг по году издания
    public void getBooksSortedByYear() {
        String sql = "SELECT * FROM books ORDER BY publishing_year";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== Книги отсортированные по году издания ===");
            while (rs.next()) {
                Book book = resultSetToBook(rs);
                System.out.println(book);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
    }

    // 6. Книги младше 2000 года
    public void getBooksBefore2000() {
        String sql = "SELECT * FROM books WHERE publishing_year < 2000";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== Книги изданные до 2000 года ===");
            while (rs.next()) {
                Book book = resultSetToBook(rs);
                System.out.println(book);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
    }

    // 7. Добавить информацию о себе и любимые книги
    public void addPersonalInfoAndBooks() {
        System.out.println("\n=== Добавление информации о себе и любимых книг ===");

        System.out.print("Введите ваше имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите вашу фамилию: ");
        String surname = scanner.nextLine();

        System.out.print("Введите ваш телефон: ");
        String phone = scanner.nextLine();

        System.out.print("Подписаны на рассылку? (true/false): ");
        boolean subscribed = scanner.nextBoolean();
        scanner.nextLine(); // consume newline

        // Добавляем посетителя
        VisitorData personalData = new VisitorData(name, surname, phone, subscribed, null);
        if (addVisitor(personalData)) {
            System.out.println("Информация о посетителе добавлена успешно!");
        }

        System.out.print("Сколько любимых книг хотите добавить? ");
        int bookCount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        int addedBooks = 0;
        for (int i = 0; i < bookCount; i++) {
            System.out.println("\nКнига " + (i + 1) + ":");
            System.out.print("Название: ");
            String bookName = scanner.nextLine();

            System.out.print("Автор: ");
            String author = scanner.nextLine();

            System.out.print("Год издания: ");
            int year = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();

            System.out.print("Издатель: ");
            String publisher = scanner.nextLine();

            Book book = new Book(bookName, author, year, isbn, publisher, name);
            if (addBook(book, name)) {
                addedBooks++;
                System.out.println("Книга '" + bookName + "' добавлена успешно!");
            } else {
                System.out.println("Не удалось добавить книгу '" + bookName + "'");
            }
        }

        // Выводим добавленные данные
        System.out.println("\n=== Добавленные данные ===");
        System.out.println("Добавлено книг: " + addedBooks + " из " + bookCount);
        getVisitorByName(name);
        getBooksByVisitor(name);
    }

    private Book resultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setPublishingYear(rs.getInt("publishing_year"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublisher(rs.getString("publisher"));
        book.setVisitorName(rs.getString("visitor_name"));
        return book;
    }

    private void getVisitorByName(String name) {
        String sql = "SELECT * FROM visitors WHERE name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println("\nИнформация о посетителе:");
                boolean found = false;
                while (rs.next()) {
                    Visitor visitor = new Visitor();
                    visitor.setId(rs.getInt("id"));
                    visitor.setName(rs.getString("name"));
                    visitor.setSurname(rs.getString("surname"));
                    visitor.setPhone(rs.getString("phone"));
                    visitor.setSubscribed(rs.getBoolean("subscribed"));
                    System.out.println(visitor);
                    found = true;
                }
                if (!found) {
                    System.out.println("Посетитель с именем '" + name + "' не найден.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
    }

    private void getBooksByVisitor(String visitorName) {
        String sql = "SELECT * FROM books WHERE visitor_name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, visitorName);
            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println("\nЛюбимые книги:");
                boolean found = false;
                while (rs.next()) {
                    Book book = resultSetToBook(rs);
                    System.out.println(book);
                    found = true;
                }
                if (!found) {
                    System.out.println("Книги для посетителя '" + visitorName + "' не найдены.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
    }

    // 8. Удалить таблицы
    public void dropTables() {
        String dropBooks = "DROP TABLE IF EXISTS books";
        String dropVisitors = "DROP TABLE IF EXISTS visitors";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(dropBooks);
            stmt.execute(dropVisitors);
            System.out.println("Таблицы книг и посетителей удалены.");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблиц: " + e.getMessage());
        }
    }

    public void showAllVisitors() {
        String sql = "SELECT * FROM visitors";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n Все посетители ");
            int count = 0;
            while (rs.next()) {
                Visitor visitor = new Visitor();
                visitor.setId(rs.getInt("id"));
                visitor.setName(rs.getString("name"));
                visitor.setSurname(rs.getString("surname"));
                visitor.setPhone(rs.getString("phone"));
                visitor.setSubscribed(rs.getBoolean("subscribed"));
                System.out.println(visitor);
                count++;
            }
            System.out.println("Всего посетителей: " + count);
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
    }


    public void showAllBooks() {
        String sql = "SELECT * FROM books";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n Все книги ");
            int count = 0;
            while (rs.next()) {
                Book book = resultSetToBook(rs);
                System.out.println(book);
                count++;
            }
            System.out.println("Всего книг: " + count);
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
    }


    public void findBooksByAuthor(String author) {
        String sql = "SELECT * FROM books WHERE author LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + author + "%");
            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println("\n=== Книги автора '" + author + "' ===");
                int count = 0;
                while (rs.next()) {
                    Book book = resultSetToBook(rs);
                    System.out.println(book);
                    count++;
                }
                System.out.println("Найдено книг: " + count);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка поиска книг: " + e.getMessage());
        }
    }


    public void showStatistics() {
        String sqlBooks = "SELECT COUNT(*) as book_count FROM books";
        String sqlVisitors = "SELECT COUNT(*) as visitor_count FROM visitors";
        String sqlSubscribed = "SELECT COUNT(*) as subscribed_count FROM visitors WHERE subscribed = true";

        try (Statement stmt = connection.createStatement();
             ResultSet rsBooks = stmt.executeQuery(sqlBooks);
             ResultSet rsVisitors = stmt.executeQuery(sqlVisitors);
             ResultSet rsSubscribed = stmt.executeQuery(sqlSubscribed)) {

            System.out.println("\n=== Статистика ===");
            if (rsBooks.next()) {
                System.out.println("Всего книг: " + rsBooks.getInt("book_count"));
            }
            if (rsVisitors.next()) {
                System.out.println("Всего посетителей: " + rsVisitors.getInt("visitor_count"));
            }
            if (rsSubscribed.next()) {
                System.out.println("Подписано на рассылку: " + rsSubscribed.getInt("subscribed_count"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения статистики: " + e.getMessage());
        }
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}