package dat.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Message(int status, String message, String timestamp) {

    public Message(int status, String message) {
        this(status, message, getCurrentTimestamp());
    }

    // Utility method to get current timestamp in the desired format
    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
