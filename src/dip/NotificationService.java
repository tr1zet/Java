package dip;

public class NotificationService {
    private MessageSender sender;

    // Внедрение зависимости через конструктор
    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void send(String message) {
        sender.send(message);
    }

    // Метод для смены отправителя
    public void setSender(MessageSender sender) {
        this.sender = sender;
    }

    // Дополнительный метод для отправки через конкретного отправителя
    public void sendWith(MessageSender tempSender, String message) {
        tempSender.send(message);
    }
}