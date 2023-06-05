package platform.exception;

public class CustomIOException extends RuntimeException {
    public CustomIOException() {
        super("IOException, please repeat request again");
    }

    public CustomIOException(String message) {
        super(message);
    }
}
