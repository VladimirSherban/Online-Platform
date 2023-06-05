package platform.exception;

public class IllegalRequestForSortedField extends RuntimeException {
    public IllegalRequestForSortedField(String message) {
        super(message);
    }
}
