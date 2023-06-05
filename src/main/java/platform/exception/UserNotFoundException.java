package platform.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String login) {
        super("User not found with login :: " + login);
    }

    public UserNotFoundException(Integer message) {
        super("User not found with id :: " + message);
    }
}
