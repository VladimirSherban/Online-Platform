package platform.service;


import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.model_dto.UserDto;
import platform.model.User;

public interface UserService {
    /**
     *
     * @param user
     * @return {@link User}
     */
    User addUser(User user);

    /**
     *
     * @param newPassword
     * @param currentPassword
     */

    void updatePassword(String newPassword, String currentPassword);

    /**
     *
     * @param userDto
     * @param email
     * @return {@link User}
     * @throws Exception
     */
    User updateUser(UserDto userDto, String email) throws Exception;

    /**
     *
     * @param image
     * @param email
     * @return Ссылка картинки
     */
    @SneakyThrows
    String updateUserImage(MultipartFile image, String email);
}
