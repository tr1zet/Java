import chat.client.ChatClient;
import chat.server.ChatServer;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Запуск Консольного Чата ---");
            System.out.println("Выберите режим:");
            System.out.println("1. Запустить СЕРВЕР и КЛИЕНТ (для тестирования)");
            System.out.println("2. Запустить только КЛИЕНТ (для подключения к удаленному серверу)");
            System.out.print("Введите число (1 или 2): ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("\n[СИСТЕМА]: Запуск сервера в фоновом потоке...");
                        Thread serverThread = new Thread(() -> new ChatServer().start(), "ChatServer-Thread");
                        serverThread.setDaemon(true);
                        serverThread.start();
                        Thread.sleep(500);

                        System.out.println("[СИСТЕМА]: Запуск первого клиента...");
                        ChatClient.main(new String[]{});
                        break;

                    case 2:
                        System.out.println("\n[СИСТЕМА]: Запуск только клиента...");
                        ChatClient.main(new String[]{});
                        break;

                    default:
                        System.out.println("Неверный выбор. Используйте 1 или 2.");
                }
            } else {
                System.out.println("Неверный ввод. Ожидалось число.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Поток был прерван.");
        } catch (Exception e) {
            System.err.println("Критическая ошибка при запуске: " + e.getMessage());
        }
    }
}