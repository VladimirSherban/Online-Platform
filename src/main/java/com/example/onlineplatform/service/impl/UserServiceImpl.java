package com.example.onlineplatform.service.impl;
import com.example.onlineplatform.model.User;
import com.example.onlineplatform.model.dto.NewPasswordDto;
import com.example.onlineplatform.model.dto.UserDto;
import com.example.onlineplatform.repository.UserRepository;
import com.example.onlineplatform.service.UserService;

import com.example.onlineplatform.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(User user) {
        if (!validationService.validate(user)) {
            return null;
        } else {
            return userRepository.save(user);
        }
    }

    @Override
    public UserDto findUser() {
        User user = getCurrentUser();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        //logger.info(userDto.toString());
        logger.info(userDto.toString() + "me");
        return userDto;
    }

    @Override
    public void updatePassword(NewPasswordDto newPasswordDto){
        logger.info(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if (encoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())){
            user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
            logger.info(userRepository.findByEmail(SecurityContextHolder
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
    public UserDto updateUser(UserDto userDto){
        User user = getCurrentUser();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setImage(userDto.getImage());
        userRepository.save(user);
        logger.info(user.toString());
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User deleteById(Integer id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void updateImage(MultipartFile image) {
        User user = getCurrentUser();
        try{
            if (image.getContentType().startsWith("image/")){
                String fileName = user.getFirstname() + "_" +
                        image.getOriginalFilename();

                image.transferTo(new File("C:\\Users\\User\\Documents\\IdeaProjects\\ResalePlatform\\src\\main\\resources\\static\\" + fileName));
                user.setImage("\\static\\" + fileName);
                userRepository.save(user);
                logger.info(image.getContentType());
                /*String uploadDir = "C:\\Users\\User\\Documents\\IdeaProjects\\ResalePlatform\\src\\main\\resources\\static\\images";
                File uploadPath = new File(uploadDir);
                File avatarFile = new File(uploadPath.getAbsolutePath() + File.separator + fileName);
                image.transferTo(avatarFile);
                user.setImage(fileName);*/
                //userRepository.save(user);
                logger.info(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getCurrentUser(){
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }
}
