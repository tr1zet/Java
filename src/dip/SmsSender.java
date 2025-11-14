package dip;

public class SmsSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("Отправка SMS: " + message);
    }
}