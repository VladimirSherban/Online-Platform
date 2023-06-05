package platform.exception;

import java.util.UUID;

/**
 * Ошибка при отсутствии картинки
 */
public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException(UUID message) {
        super("Picture doesn't exist wit UUID :: ".concat(message.toString()));
    }
}