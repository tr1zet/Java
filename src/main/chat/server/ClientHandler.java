package chat.server;

import chat.model.Message;
import chat.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;


public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    private final ChatServer server;
    private ObjectOutputStream outputStream;
    private String nickname;
    private volatile boolean isRunning = true;

    public ClientHandler(Socket clientSocket, ChatServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            // 1. Приём никнейма
            Message initialMessage = (Message) inputStream.readObject();
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
            while (isRunning) {
                Message message = (Message) inputStream.readObject();
                handleMessage(message);
            }
        } catch (EOFException e) {
            logger.info("Соединение с клиентом {} корректно закрыто.", nickname);
        } catch (IOException e) {
            logger.warn("Соединение с клиентом {} разорвано. Причина: {}", nickname, e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error("Ошибка десериализации сообщения от клиента {}: {}", nickname, e.getMessage());
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
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("Ошибка отправки сообщения клиенту {}: {}", nickname, e.getMessage());
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