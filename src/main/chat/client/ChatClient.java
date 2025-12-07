package chat.client;

import chat.model.Message;
import chat.model.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class ChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    private Socket socket;
    private String nickname;
    private ObjectOutputStream outputStream;
    private final AtomicBoolean isConnected = new AtomicBoolean(false);

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }

    public boolean isConnected() {
        return isConnected.get();
    }

    public String getNickname() {
        return nickname;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите ваш никнейм: ");
            nickname = scanner.nextLine().trim();
            if (nickname.isEmpty()) {
                System.out.println("Никнейм не может быть пустым.");
                return;
            }

            // 2. Подключение к серверу
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            isConnected.set(true);

            // 3. Отправка никнейма на сервер
            sendMessage(new Message(MessageType.CONNECT, nickname, null, null));
            System.out.println("Подключено к серверу как " + nickname);

            // Запуск потока для чтения сообщений от сервера
            Thread readThread = new Thread(() -> readMessages(inputStream), "Client-Read-Thread");
            readThread.start();

            // Запуск потока для обработки ввода пользователя
            Thread inputThread = new Thread(new ClientInputHandler(this), "Client-Input-Thread");
            inputThread.start();

            readThread.join();
            inputThread.interrupt();

        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            disconnect();
        }
    }


    private void readMessages(ObjectInputStream inputStream) {
        try {
            while (isConnected.get()) {
                Message message = (Message) inputStream.readObject();
                handleMessage(message);
            }
        } catch (EOFException e) {
            System.err.println("\n[СЕРВЕР]: Соединение закрыто сервером.");
        } catch (IOException | ClassNotFoundException e) {
            if (isConnected.get()) {
                System.err.println("\n[СЕРВЕР]: Соединение потеряно.");
            }
        } finally {
            disconnect();
        }
    }


    private void handleMessage(Message message) {
        switch (message.type()) {
            case BROADCAST, PRIVATE -> System.out.println("\n" + message.getDisplayFormat()); // Требование 6
            case USER_LIST -> {
                System.out.println("\n--- Активные пользователи ---"); // Требование 4
                String[] users = message.text().split(",");
                for (String user : users) {
                    if (!user.equals(nickname)) {
                        System.out.println("- " + user);
                    }
                }
                System.out.println("-----------------------------\n");
            }
            default -> System.out.println("\n[СИСТЕМА]: Получено системное сообщение.");
        }
        System.out.print(">");
    }


    public synchronized void sendMessage(Message message) {
        if (!isConnected.get()) return;
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
            disconnect();
        }
    }


    public void disconnect() {
        if (isConnected.compareAndSet(true, false)) {
            System.out.println("\nОтключение от сервера...");
            try {
                // Отправляем серверу сообщение о выходе
                sendMessage(new Message(MessageType.DISCONNECT, nickname, null, null));
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                // Игнорируем
            }
            System.exit(0);
        }
    }
}