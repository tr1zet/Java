package chat.server;

import chat.model.Message;
import chat.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private static final int PORT = 8080;


    private final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new ChatServer().start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Сервер чата запущен на порту {}", PORT);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Получено новое подключение от: {}", clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            logger.error("Критическая ошибка сервера: {}", e.getMessage());
        }
    }

    public void addClient(String nickname, ClientHandler handler) {
        clients.put(nickname, handler);
    }

    public void removeClient(String nickname) {
        clients.remove(nickname);
    }

    public Set<String> getActiveUsers() {
        return Collections.unmodifiableSet(clients.keySet());
    }

    public void broadcast(Message message) {
        logger.info("Отправка BROADCAST: {}", message.getDisplayFormat());
        clients.values().forEach(handler -> handler.sendMessage(message));
    }

    public void sendPrivateMessage(Message message) {
        ClientHandler recipientHandler = clients.get(message.recipient());

        if (recipientHandler != null) {
            logger.info("Отправка PRIVATE: {}", message.getDisplayFormat());
            recipientHandler.sendMessage(message);

            ClientHandler senderHandler = clients.get(message.sender());
            if (senderHandler != null && senderHandler != recipientHandler) {
                senderHandler.sendMessage(message);
            }
        } else {
            logger.warn("Не удалось найти получателя '{}' для личного сообщения.", message.recipient());

            ClientHandler senderHandler = clients.get(message.sender());
            if (senderHandler != null) {
                senderHandler.sendMessage(new Message(MessageType.PRIVATE, "SERVER", message.sender(),
                        "Пользователь " + message.recipient() + " не найден."));
            }
        }
    }
}