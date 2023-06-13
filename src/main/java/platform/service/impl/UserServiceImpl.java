package platform.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.model.User;
import platform.repository.UserRepository;
import platform.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${image.path}")
    private String imagePath;



    @Override
    public User createUser(User user) {
            return userRepository.save(user);
    }

    @Override
    public UserDto findUser() {
        User user = getUser();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public void updatePassword(NewPasswordDto newPasswordDto) {
        logger.info(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findFirstByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if (encoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
            logger.info(userRepository.findFirstByEmail(SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getName()).get()
                    .getPassword());
            userRepository.save(user);
        } else {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = getUser();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());

        userRepository.save(user);
        logger.info(user.toString());
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void updateImage(MultipartFile image) throws IOException {

        User user = getUser();
        createPath(imagePath, logger);
        if (image.getContentType().startsWith("image/")) {
            String fileName = UUID.randomUUID() + "_" +
                    image.getOriginalFilename();
            image.transferTo(new File(imagePath + fileName));
            user.setImage("\\static\\" + fileName);
            userRepository.save(user);
            logger.info(image.getContentType());
            logger.info(fileName);
        }
    }

    private User getUser() {
        return userRepository.findFirstByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }
    static void createPath(String imagePath, Logger logger) {
        Path path = Paths.get(imagePath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                logger.info("Путь создан: " + path);
            } catch (IOException e) {
                logger.error("Не получилось создать путь: " + e.getMessage());
            }
        } else {
            logger.info("Такой путь уже есть: " + path);
        }
    }

}
