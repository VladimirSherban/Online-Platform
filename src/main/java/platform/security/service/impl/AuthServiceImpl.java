package platform.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import platform.dto.RegReqDto;
import platform.mapper.UserMapper;
import platform.model.User;
import platform.repository.UserRepository;
import platform.security.dto.Role;
import platform.security.service.AuthService;
import platform.service.UserService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserService userService;


    @Override
    public boolean login(String userName, String password) throws Exception {
        logger.info("Логинимся");
        Optional<User> user = userRepository.findByEmail(userName);
        if (user.isEmpty()) {
            throw new Exception("Пользователь с таким именем не зарегистрирован");
        }
        return encoder.matches(password, user.get().getPassword());
    }

    @Override
    public boolean register(RegReqDto regReqDto, Role role) {

        logger.info("Регистрируемся");
        User user = userMapper.toEntity(regReqDto);
        userService.addUser(user);
        return true;

    }

}
