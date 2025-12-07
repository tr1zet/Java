package chat.model;

import java.io.Serializable;


public record Message(
        MessageType type,
        String sender,
        String recipient,
        String text
) implements Serializable {


    public String getDisplayFormat() {
        String recipientDisplay = recipient;
        if (type == MessageType.BROADCAST) {
            recipientDisplay = "ALL";
        }

        return String.format("[%s -> %s]: %s", sender, recipientDisplay, text);
    }
}