package platform.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException() {
        super("Не-а, тебе не пройти.");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
