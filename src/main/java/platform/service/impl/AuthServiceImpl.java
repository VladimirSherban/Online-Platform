package platform.service.impl;

import platform.dto.RegReqDto;
import platform.exception.RegisterException;
import platform.mapper.UserRegisterMapper;
import platform.model.User;
import platform.repository.UserRepository;
import platform.security.enumki.Role;
import platform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Аутендификация и регистрация пользователей
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager manager;
    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean login(String userName, String password) {
        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return true;
    }

    @Override
    public boolean register(RegReqDto registerReq) {
        Optional<User> firstByEmail = userRepository.findFirstByEmail(registerReq.getUsername());
        if (firstByEmail.isPresent()) {
            throw new RegisterException("This email is already in use another account :: " + registerReq.getUsername());
        }
        User entity = userRegisterMapper.toEntity(registerReq);
        String pass = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(pass);
        entity.setRoleGroup(Role.ROLE_USER);
        userRepository.save(entity);
        return true;
    }
}
