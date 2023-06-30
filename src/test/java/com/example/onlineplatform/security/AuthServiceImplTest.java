package com.example.onlineplatform.security;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import platform.model.User;
import platform.repository.UserRepository;
import platform.security.service.impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void login_ValidCredentials_ReturnsTrue() throws Exception {
        String userName = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(userName);
        user.setPassword(encoder.encode(password));

        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(user));
        when(encoder.matches(password, user.getPassword())).thenReturn(true);

        boolean result = authService.login(userName, password);

        assertTrue(result);
        verify(userRepository, times(1)).findByEmail(userName);
        verify(encoder, times(1)).matches(password, user.getPassword());
    }

}
