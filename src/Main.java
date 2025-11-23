import java.util.List;
import java.util.Scanner;
import model.VisitorData;

public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        if (!dbManager.isConnected()) {
            System.out.println("Не удалось подключиться к базе данных. Программа завершена.");
            return;
        }

        MusicManager musicManager = new MusicManager(dbManager.getConnection());
        BookManager bookManager = new BookManager(dbManager.getConnection());
        JSONParser jsonParser = new JSONParser();
        Scanner scanner = new Scanner(System.in);

        // Загружаем данные из JSON файла
        List<VisitorData> visitorsData = jsonParser.parseVisitorsFromFile("books.json");

        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Выберите опцию: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        musicManager.getAllMusic();
                        break;
                    case 2:
                        musicManager.getMusicWithoutMT();
                        break;
                    case 3:
                        addFavoriteSongInteractive(musicManager, scanner);
                        break;
                    case 4:
                        if (visitorsData != null) {
                            jsonParser.printVisitorsData(visitorsData);
                            bookManager.importDataFromJSON(visitorsData);
                        } else {
                            System.out.println("Данные из JSON не загружены.");
                        }
                        break;
                    case 5:
                        bookManager.getBooksSortedByYear();
                        break;
                    case 6:
                        bookManager.getBooksBefore2000();
                        break;
                    case 7:
                        bookManager.addPersonalInfoAndBooks();
                        break;
                    case 8:
                        bookManager.dropTables();
                        break;
                    case 9:
                        bookManager.showAllVisitors();
                        break;
                    case 10:
                        bookManager.showAllBooks();
                        break;
                    case 11:
                        musicManager.showTableStructure();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
                scanner.nextLine(); // очистка буфера
            }
        }

        // Очистка ресурсов
        bookManager.closeScanner();
        scanner.close();
        dbManager.closeConnection();
        System.out.println("Программа завершена.");
    }

    private static void printMenu() {
        System.out.println("\n Меню управления музыкой и книгами ");
        System.out.println("1. Показать все музыкальные композиции");
        System.out.println("2. Показать композиции без букв 'm' и 't'");
        System.out.println("3. Добавить любимую композицию");
        System.out.println("4. Анализ books.json и импорт данных");
        System.out.println("5. Показать книги отсортированные по году");
        System.out.println("6. Показать книги до 2000 года");
        System.out.println("7. Добавить информацию о себе и книги");
        System.out.println("8. Удалить таблицы книг и посетителей");
        System.out.println("9. Показать всех посетителей");
        System.out.println("10. Показать все книги");
        System.out.println("11. Показать структуру таблицы music");
        System.out.println("0. Выход");
    }

    private static void addFavoriteSongInteractive(MusicManager musicManager, Scanner scanner) {
        System.out.print("Введите название композиции: ");
        String name = scanner.nextLine();

        musicManager.addFavoriteSong(name);
    }
}