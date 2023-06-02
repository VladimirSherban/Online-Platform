package com.example.onlineplatform.service;

import com.example.onlineplatform.model.User;
import com.example.onlineplatform.model.dto.NewPasswordDto;
import com.example.onlineplatform.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User createUser(User user);

    UserDto findUser();

    void updatePassword(NewPasswordDto newPasswordDto);

    UserDto updateUser(UserDto userDto);

    User deleteById(Integer id);

    List<User> findAll();

    void updateImage(MultipartFile image);

    User getCurrentUser();
}
