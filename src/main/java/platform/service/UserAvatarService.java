package platform.service;

import platform.exception.AvatarNotFoundException;
import platform.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface UserAvatarService {
    /**
     * Сохранение аватара пользователя в базу.
     * Картинка кодируется в Base64
     * @param image
     * @param login
     * @throws IOException
     * @throws UserNotFoundException
     * @return String UUID
     */
    String save(MultipartFile image, String login);
    /**
     * Получение аватарки пользователя по UUID
     * @param uuid
     * @throws AvatarNotFoundException
     * @return byte[]
     */
    byte[] download(UUID uuid);

}
