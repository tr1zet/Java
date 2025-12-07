package chat.model;


public record Message(
        MessageType type,
        String sender,
        String recipient,
        String text
) {


    public String getDisplayFormat() {
        String recipientDisplay = recipient;
        if (type == MessageType.BROADCAST) {
            recipientDisplay = "ALL";
        }

        return String.format("[%s -> %s]: %s", sender, recipientDisplay, text);
    }
}