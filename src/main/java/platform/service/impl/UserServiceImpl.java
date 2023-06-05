package platform.service.impl;


import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.dto.response_wrapper.ResponseWrapperUserDto;
import platform.exception.ForbiddenException;
import platform.exception.UserNotFoundException;
import platform.mapper.UserMapper;
import platform.model.User;
import platform.repository.UserRepository;
import platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для UserController
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
    public ResponseWrapperUserDto getUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return new ResponseWrapperUserDto(users.size(), userMapper.toDtos(users));
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
     * @param login
     * @throws UserNotFoundException
     * @return User
     */
    private User findUserByLogin(String login) {
        return userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
    }
}
