import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        connect();
        if (connection != null) {
            executeMusicScript();
        }
    }

    private void connect() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                System.out.println("SQLite JDBC драйвер зарегистрирован");
            } catch (ClassNotFoundException e) {
                System.out.println("SQLite JDBC драйвер не найден в classpath");
                System.out.println("Добавьте sqlite-jdbc-3.42.0.0.jar в classpath");
                return;
            }

            Properties props = new Properties();
            try (InputStream input = new FileInputStream("config.properties")) {
                props.load(input);
                System.out.println("Конфигурационный файл загружен");
            } catch (IOException e) {
                System.out.println("Файл config.properties не найден, используются настройки по умолчанию");
                props.setProperty("db.url", "jdbc:sqlite:music_books.db");
            }

            String url = props.getProperty("db.url");
            System.out.println("Подключаемся к: " + url);
            connection = DriverManager.getConnection(url);
            System.out.println("Подключение к базе данных установлено успешно!");

        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
            System.out.println("Убедитесь, что драйвер SQLite добавлен в classpath");
        }
    }

    private void executeMusicScript() {
        if (connection == null) {
            System.out.println("Нет подключения к базе данных, невозможно выполнить SQL скрипт");
            return;
        }

        try {
            if (!Files.exists(Paths.get("music-create.sql"))) {
                System.out.println("Файл music-create.sql не найден, создаем таблицу по умолчанию");
                createMusicTableFallback();
                return;
            }

            String sqlScript;
            try {
                sqlScript = new String(Files.readAllBytes(Paths.get("music-create.sql")));
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла music-create.sql: " + e.getMessage());
                createMusicTableFallback();
                return;
            }


            sqlScript = sqlScript.replace("study.music", "music");

            System.out.println("Выполняем SQL скрипт...");


            try (Statement stmt = connection.createStatement()) {
                String[] queries = sqlScript.split(";");
                for (String query : queries) {
                    String trimmedQuery = query.trim();
                    if (!trimmedQuery.isEmpty()) {
                        try {
                            stmt.execute(trimmedQuery);
                            System.out.println("Выполнен запрос: " +
                                    (trimmedQuery.length() > 50 ?
                                            trimmedQuery.substring(0, 50) + "..." :
                                            trimmedQuery));
                        } catch (SQLException e) {
                            System.out.println("Ошибка выполнения запроса: " + e.getMessage());
                        }
                    }
                }
                System.out.println("SQL скрипт для таблицы music выполнен успешно.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка выполнения SQL скрипта: " + e.getMessage());
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
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM music")) {
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
                } else {
                    System.out.println("В таблице music уже есть данные.");
                }
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