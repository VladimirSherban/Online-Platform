package platform.service;

import org.springframework.web.multipart.MultipartFile;
import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.model.User;

import java.io.IOException;

public interface UserService {
    User createUser(User user);


    UserDto findUser();


    void updatePassword(NewPasswordDto newPasswordDto);

    UserDto updateUser(UserDto userDto);

    void updateImage(MultipartFile image) throws IOException;
}
