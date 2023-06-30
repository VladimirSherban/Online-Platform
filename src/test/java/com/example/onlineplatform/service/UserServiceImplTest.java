package com.example.onlineplatform.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import platform.dto.model_dto.UserDto;
import platform.model.User;
import platform.repository.UserRepository;
import platform.security.dto.Role;
import platform.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void addUser_ShouldReturnAddedUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.existsByEmailContains(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User addedUser = userService.addUser(user);

        assertEquals(user.getEmail(), addedUser.getEmail());
        assertEquals(user.getPassword(), addedUser.getPassword());
    }

    @Test()
    public void addUser_ExistingUser_ShouldThrowValidationException() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.existsByEmailContains(anyString())).thenReturn(true);

        userService.addUser(user);
    }

    @Test
    public void addUser_NoRoleSpecified_ShouldSetUserRole() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.existsByEmailContains(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User addedUser = userService.addUser(user);

        assertEquals(Role.USER, addedUser.getRole());
    }


    @Test
    public void updateUser_ShouldReturnUpdatedUser() throws Exception {
        String email = "test@example.com";
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setPhone("+123456789");

        User existingUser = new User();
        existingUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.updateUser(userDto, email);

        assertEquals(userDto.getFirstName(), updatedUser.getFirstName());
        assertEquals(userDto.getLastName(), updatedUser.getLastName());
        assertEquals(userDto.getPhone(), updatedUser.getPhone());
    }


    @Test
    public void getUserById_ExistingId_ShouldReturnUser() {
        Integer id = 1;
        User user = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(id);

        assertEquals(user, retrievedUser);
    }


    @Test
    public void getUsers_ExistingEmail_ShouldReturnUser() {
        String email = "test@example.com";
        User user = new User();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUsers(email);

        assertEquals(user, retrievedUser);
    }


    @Test
    public void updateRole_ShouldReturnUpdatedUser() {
        Integer id = 1;
        Role role = Role.ADMIN;

        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.updateRole(id, role);

        assertEquals(role, updatedUser.getRole());
    }
}
