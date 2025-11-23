import java.sql.*;

public class MusicManager {
    private Connection connection;

    public MusicManager(Connection connection) {
        this.connection = connection;
    }

    // 1. Получить все музыкальные композиции
    public void getAllMusic() {
        String sql = "SELECT * FROM music";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== Все музыкальные композиции ===");
            while (rs.next()) {
                System.out.printf("ID: %d, Название: %s%n",
                        rs.getInt("id"),
                        rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
    }

    // 2. Получить композиции без букв m и t (без учёта регистра)
    public void getMusicWithoutMT() {
        String sql = "SELECT * FROM music WHERE LOWER(name) NOT LIKE '%m%' AND LOWER(name) NOT LIKE '%t%'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== Композиции без букв 'm' и 't' ===");
            while (rs.next()) {
                System.out.printf("ID: %d, Название: %s%n",
                        rs.getInt("id"),
                        rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
    }

    // 3. Добавить любую свою любимую композицию
    public void addFavoriteSong(String name) {
        // Находим максимальный ID для создания нового
        String maxIdSql = "SELECT MAX(id) as max_id FROM music";
        int newId = 1;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(maxIdSql)) {

            if (rs.next()) {
                newId = rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения максимального ID: " + e.getMessage());
            return;
        }

        // Добавляем новую композицию
        String sql = "INSERT INTO music (id, name) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newId);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Композиция '" + name + "' успешно добавлена с ID: " + newId);
        } catch (SQLException e) {
            System.out.println("Ошибка добавления композиции: " + e.getMessage());
        }
    }

    // Дополнительный метод для проверки структуры таблицы
    public void showTableStructure() {
        String sql = "PRAGMA table_info(music)";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== Структура таблицы music ===");
            while (rs.next()) {
                System.out.printf("Колонка: %s, Тип: %s, Nullable: %s%n",
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("notnull") == 1 ? "NO" : "YES");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения структуры таблицы: " + e.getMessage());
        }
    }
}