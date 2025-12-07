package chat.client;

import chat.model.Message;
import chat.model.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ClientInputHandler implements Runnable {
    private final ChatClient client;
    private final BufferedReader consoleReader;

    public ClientInputHandler(ChatClient client) {
        this.client = client;
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Вы подключены. Введите '/help' для списка команд.");

        try {
            while (!Thread.currentThread().isInterrupted() && client.isConnected()) {
                System.out.print(">");
                String input = consoleReader.readLine();

                if (input == null) break;

                if (input.trim().isEmpty()) continue;

                if (input.startsWith("/")) {
                    handleCommand(input.trim().toLowerCase());
                } else {
                    System.out.println("Неверная команда. Введите '/help' или /all для отправки.");
                }
            }
        } catch (IOException e) {
        } finally {
            client.disconnect();
        }
    }

    private void handleCommand(String command) throws IOException {
        switch (command) {
            case "/help" -> displayHelp();
            case "/all" -> sendBroadcastMessage();
            case "/private" -> sendPrivateMessage();
            case "/list" -> client.sendMessage(new Message(MessageType.LIST_REQUEST, client.getNickname(), null, null));
            case "/exit" -> client.disconnect();
            default -> System.out.println("Неизвестная команда. Используйте /help.");
        }
    }

    private void displayHelp() {
        System.out.println("\n--- Команды ---");
        System.out.println("/help\t\t\t\t- Показать это меню.");
        System.out.println("/all\t\t\t\t- Отправить широковещательное сообщение.");
        System.out.println("/private\t\t\t- Отправить личное сообщение.");
        System.out.println("/list\t\t\t\t- Запросить список активных пользователей.");
        System.out.println("/exit\t\t\t\t- Отключиться от сервера.");
        System.out.println("----------------\n");
    }

    private void sendBroadcastMessage() throws IOException {
        System.out.print("Введите сообщение для ВСЕХ: ");
        String text = consoleReader.readLine();
        if (text != null && !text.trim().isEmpty()) {
            client.sendMessage(new Message(MessageType.BROADCAST, client.getNickname(), "ALL", text));
        }
    }

    private void sendPrivateMessage() throws IOException {
        // 1. Запрос списка
        client.sendMessage(new Message(MessageType.LIST_REQUEST, client.getNickname(), null, null));
        System.out.println("Запрошен список пользователей. Дождитесь его отображения, затем введите получателя.");

        // 2. Ввод получателя
        System.out.print("Введите никнейм получателя: ");
        String recipient = consoleReader.readLine();

        if (recipient == null || recipient.trim().isEmpty()) {
            System.out.println("Отправка отменена.");
            return;
        }

        // 3. Ввод текста
        System.out.print("Введите личное сообщение: ");
        String text = consoleReader.readLine();

        if (text != null && !text.trim().isEmpty()) {
            client.sendMessage(new Message(MessageType.PRIVATE, client.getNickname(), recipient, text));
        }
    }
}