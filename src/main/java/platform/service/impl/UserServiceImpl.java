package platform.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.exception.ForbiddenException;
import platform.exception.RegisterException;
import platform.exception.UserNotFoundException;
import platform.mapper.UserMapper;
import platform.model.User;
import platform.repository.UserRepository;
import platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


/**
 * Сервис для UserController
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger;

    @Override
    public UserDto findUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto findUser(String login) {
        User user = userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
        return userMapper.toDto(user);
    }


    @Override
    public NewPasswordDto setPassword(NewPasswordDto newPasswordDto, String login) {
        User user = findUserByLogin(login);
        if (!passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new ForbiddenException("Invalid old password");
        }
        user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);
        return newPasswordDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto) {
        User updated = findUserByLogin(userDto.getEmail());
        updated.setFirstName(userDto.getFirstName());
        updated.setLastName(userDto.getLastName());
        updated.setPhone(userDto.getPhone());
        return userMapper.toDto(userRepository.save(updated));
    }

    /**
     * Поиск сущности User по логину
     *
     * @param login
     * @return User
     */
    private User findUserByLogin(String login) {
        return userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    public NewPasswordDto updatePassword(NewPasswordDto newPasswordDto) {
        logger.info(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findFirstByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if (passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
            logger.info(userRepository.findFirstByEmail(SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getName()).get()
                    .getPassword());
            userRepository.save(user);
            return newPasswordDto;
        } else {
            throw new RegisterException("Incorrect password");
        }
    }

}
