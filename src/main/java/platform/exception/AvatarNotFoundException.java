package platform.exception;

import java.util.UUID;

public class AvatarNotFoundException extends RuntimeException {

    public AvatarNotFoundException(UUID uuid) {
        super("Avatar not found :: " + uuid.toString());
    }
}
