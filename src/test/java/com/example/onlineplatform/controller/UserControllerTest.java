package com.example.onlineplatform.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import platform.controller.UserController;
import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.mapper.UserMapper;
import platform.model.Image;
import platform.model.User;
import platform.repository.UserRepository;
import platform.security.dto.Role;
import platform.service.ImageService;
import platform.service.UserService;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;


    @Test
    public void getUser_ReturnsUserDto() {

        int id = 1;
        User user = new User();
        user.setId(id);

        UserDto userDto = new UserDto();
        userDto.setId(id);

        when(userService.getUserById(id)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUser(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }


    @Test
    public void updatePassword_ReturnsNewPasswordDto() {
        NewPasswordDto newPasswordDto = new NewPasswordDto("","");

        ResponseEntity<NewPasswordDto> response = userController.updatePassword(newPasswordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newPasswordDto, response.getBody());
    }

    @Test
    public void updateUserImage_ReturnsImageString() {

        MultipartFile image = mock(MultipartFile.class);
        String username = "testUser";
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn(username);

        String imageString = "imageString";
        when(userService.updateUserImage(image, username)).thenReturn(imageString);


        ResponseEntity<String> response = userController.updateUserImage(image);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imageString, response.getBody());
    }

    @Test
    public void updateUser_ReturnsUserDto() throws Exception {

        UserDto userDto = new UserDto();
        String username = "testUser";
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn(username);

        User updatedUser = new User();
        when(userService.updateUser(userDto, username)).thenReturn(updatedUser);

        UserDto updatedUserDto = new UserDto();
        when(userMapper.toDto(updatedUser)).thenReturn(updatedUserDto);

        ResponseEntity<UserDto> response = userController.updateUser(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUserDto, response.getBody());
    }

    @Test
    public void updateRole_ReturnsUserDto() {

        int id = 1;
        Role role = Role.ADMIN;

        User user = new User();
        user.setId(id);

        UserDto userDto = new UserDto();
        userDto.setId(id);

        when(userService.updateRole(id, role)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.updateRole(id, role);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    public void getImageById_ReturnsImageByteArray() {
        int id = 1;

        Image image = new Image();
        image.setImage(new byte[] {1, 2, 3});

        when(imageService.getImageById(id)).thenReturn(image);

        ResponseEntity<byte[]> response = userController.getImageById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(image.getImage(), response.getBody());
    }
}