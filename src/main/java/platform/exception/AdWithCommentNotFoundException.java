package platform.exception;

public class AdWithCommentNotFoundException extends RuntimeException {

    public AdWithCommentNotFoundException(Integer adsMessage, Integer commentMessage) {
        super("Ad with id :: " + adsMessage + " doesn't contain AdComment with id :: " + commentMessage);
    }
}

