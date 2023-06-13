package platform.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.model_dto.UserDto;
import platform.model.User;
import platform.repository.UserRepository;
import platform.security.dto.Role;
import platform.security.service.impl.UserDetailsServiceImpl;
import platform.service.ImageService;
import platform.service.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import static platform.security.service.impl.SecurityUtils.getUserDetailsFromContext;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final ImageService imageService;

    //в любом случае у нас есть пользователь по дефолту
    @PostConstruct
    public void addDefaultUser() {
        logger.info("Метод создания дефолтного пользователя");
        User user = new User();
        user.setPassword("password");
        user.setEmail("user@email.com");
        user.setPhone("+79999999999");
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setRole(Role.USER);
        addUser(user);
    }

    @Override
    public User addUser(User user) {
        if (userRepository.existByEmail(user.getEmail())) {
            throw new ValidationException("Пользователь с таким email уже существует");
        }
        if (user.getRole() == null) {
            //если не назначили пользователю роль
            user.setRole(Role.USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String newPassword, String currentPassword) {
        logger.info("Метод смены пароля");
        UserDetails userDetails = getUserDetailsFromContext();
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Некорректный пароль для замены");
        }
        userDetailsService.updatePassword(userDetails, passwordEncoder.encode(newPassword));

    }

    @Override
    public User updateUser(UserDto userDto, String email) throws Exception {
        logger.info("Метод обновления пользователя");
        User user = userRepository.findFirstByEmail(email).orElseThrow(()-> new Exception("Пользователь не найден"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        return userRepository.save(user);
    }
    @SneakyThrows
    @Override
    public String updateUserImage(MultipartFile image, String email) {

        logger.info("Метод замены/загрузки картинки пользователю");
        User user = userRepository.findFirstByEmail(email).orElseThrow();
        user.setImage(imageService.upload(image));
        return "/users/image/" + userRepository.save(user).getImage().getId();

    }
    @Override
    public User getUserById(long id) {
        //чтоб в контроллере вернуть через дто
        return userRepository.findById(id).orElseThrow();

    }

    @Override
    public User getUsers(String email) {
        //вывести по email
        return userRepository.findFirstByEmail(email).orElseThrow();

    }

    @Override
    public User updateRole(long id, Role role) {
        //чтоб в контроллере смогли поставить админку обычному пользователю
        User user = getUserById(id);
        user.setRole(role);
        return userRepository.save(user);

    }

}
