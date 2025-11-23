import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        // Регистрируем драйвер SQLite
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC драйвер не найден: " + e.getMessage());
            return;
        }

        connect();
        if (connection != null) {
            executeMusicScript();
        }
    }

    private void connect() {
        try {
            Properties props = new Properties();
            // Создаем файл config.properties если его нет
            try {
                props.load(new FileInputStream("config.properties"));
            } catch (IOException e) {
                System.out.println("Файл config.properties не найден, используются настройки по умолчанию");
                // Создаем default properties
                props.setProperty("db.url", "jdbc:sqlite:music_books.db");
            }

            String url = props.getProperty("db.url");
            connection = DriverManager.getConnection(url);
            System.out.println("Подключение к базе данных установлено: " + url);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }

    private void executeMusicScript() {
        if (connection == null) {
            System.out.println("Нет подключения к базе данных, невозможно выполнить SQL скрипт");
            return;
        }

        try {
            // Проверяем существование файла
            if (!Files.exists(Paths.get("music-create.sql"))) {
                System.out.println("Файл music-create.sql не найден, создаем таблицу по умолчанию");
                createMusicTableFallback();
                return;
            }

            // Читаем SQL файл
            String sqlScript = new String(Files.readAllBytes(Paths.get("music-create.sql")));

            // Убираем схему study. если она вызывает проблемы
            sqlScript = sqlScript.replace("study.music", "music");

            // Выполняем скрипт
            try (Statement stmt = connection.createStatement()) {
                // Разделяем скрипт на отдельные запросы
                String[] queries = sqlScript.split(";");
                for (String query : queries) {
                    String trimmedQuery = query.trim();
                    if (!trimmedQuery.isEmpty()) {
                        try {
                            stmt.execute(trimmedQuery);
                        } catch (SQLException e) {
                            System.out.println("Ошибка выполнения запроса: " + trimmedQuery);
                            System.out.println("Сообщение: " + e.getMessage());
                        }
                    }
                }
                System.out.println("SQL скрипт для таблицы music выполнен успешно.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка выполнения SQL скрипта: " + e.getMessage());
            // Если скрипт не выполнился, создаем таблицу по умолчанию
            createMusicTableFallback();
        }
    }

    private void createMusicTableFallback() {
        if (connection == null) {
            System.out.println("Нет подключения к базе данных, невозможно создать таблицу");
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS music (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблица music создана успешно.");

            // Проверяем, есть ли данные в таблице
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM music");
            if (rs.next() && rs.getInt("count") == 0) {
                // Добавляем тестовые данные только если таблица пустая
                String insertSQL = "INSERT INTO music (id, name) VALUES " +
                        "(1, 'Bohemian Rhapsody'), " +
                        "(2, 'Stairway to Heaven'), " +
                        "(3, 'Imagine'), " +
                        "(4, 'Sweet Child O Mine'), " +
                        "(5, 'Hey Jude'), " +
                        "(6, 'Hotel California'), " +
                        "(7, 'Billie Jean'), " +
                        "(8, 'Wonderwall'), " +
                        "(9, 'Smells Like Teen Spirit'), " +
                        "(10, 'Let It Be'), " +
                        "(11, 'I Want It All'), " +
                        "(12, 'November Rain'), " +
                        "(13, 'Losing My Religion'), " +
                        "(14, 'One'), " +
                        "(15, 'With or Without You'), " +
                        "(16, 'Sweet Caroline'), " +
                        "(17, 'Yesterday'), " +
                        "(18, 'Dont Stop Believin'), " +
                        "(19, 'Crazy Train'), " +
                        "(20, 'Always')";

                stmt.execute(insertSQL);
                System.out.println("Добавлены тестовые данные в таблицу music.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы music: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Подключение к базе данных закрыто.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия подключения: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}