package chat.server;

import chat.model.Message;
import chat.model.MessageType;
import com.google.gson.Gson; // НОВЫЙ ИМПОРТ
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;


public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    private final ChatServer server;
    private PrintWriter writer;
    private BufferedReader reader;
    private final Gson gson = new Gson();

    private String nickname;
    private volatile boolean isRunning = true;

    public ClientHandler(Socket clientSocket, ChatServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // 1. Приём никнейма
            String initialJson = reader.readLine(); // Читаем JSON строку
            if (initialJson == null) {
                logger.warn("Соединение закрыто до отправки никнейма.");
                return;
            }

            Message initialMessage = gson.fromJson(initialJson, Message.class);

            if (initialMessage.type() == MessageType.CONNECT) {
                nickname = initialMessage.sender();
                server.addClient(nickname, this);
                logger.info("Новый клиент подключен: {}", nickname);
                server.broadcast(new Message(MessageType.BROADCAST, "SERVER", "ALL", nickname + " присоединился к чату."));
            } else {
                logger.warn("Неверный первый тип сообщения от сокета: {}", clientSocket.getInetAddress());
                clientSocket.close();
                return;
            }

            // 2. Основной цикл обработки сообщений
            String json;
            while (isRunning && (json = reader.readLine()) != null) {
                // БЕЗОПАСНАЯ ДЕСЕРИАЛИЗАЦИЯ: Преобразуем JSON в объект Message
                Message message = gson.fromJson(json, Message.class);
                handleMessage(message);
            }
        } catch (IOException e) {
            logger.warn("Соединение с клиентом {} разорвано. Причина: {}", nickname, e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void handleMessage(Message message) {
        logger.info("Входящее сообщение [{} -> {}]: {}", message.sender(), message.recipient(), message.text());

        switch (message.type()) {
            case BROADCAST -> server.broadcast(message);
            case PRIVATE -> server.sendPrivateMessage(message);
            case LIST_REQUEST -> sendUserList();
            case DISCONNECT -> isRunning = false;
            default -> logger.warn("Получен неподдерживаемый тип сообщения: {}", message.type());
        }
    }


    public synchronized void sendMessage(Message message) {
        try {
            String json = gson.toJson(message);
            writer.println(json); // Отправляем строку
        } catch (Exception e) {
            logger.error("Ошибка отправки JSON сообщения клиенту {}: {}", nickname, e.getMessage());
            cleanup();
        }
    }

    private void sendUserList() {
        String userList = String.join(",", server.getActiveUsers());
        Message listMessage = new Message(MessageType.USER_LIST, "SERVER", nickname, userList);
        sendMessage(listMessage);
    }

    private void cleanup() {
        if (nickname != null) {
            server.removeClient(nickname);
            server.broadcast(new Message(MessageType.BROADCAST, "SERVER", "ALL", nickname + " покинул чат."));
            logger.info("Клиент {} удалён из списка активных.", nickname);
        }
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка при закрытии сокета клиента: {}", e.getMessage());
        }
    }
}