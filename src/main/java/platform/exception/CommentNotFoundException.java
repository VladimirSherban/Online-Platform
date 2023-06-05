package platform.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Integer message) {
        super("AdComment not found with id :: " + message);
    }
}
