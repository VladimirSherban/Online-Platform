package com.example.onlineplatform.security.service.impl;

import com.example.onlineplatform.model.User;
import com.example.onlineplatform.repository.UserRepository;
import com.example.onlineplatform.security.dto.RegisterReq;
import com.example.onlineplatform.security.dto.Role;
import com.example.onlineplatform.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public boolean login(String userName, String password) {

        /*if (!userRepository.existsByEmail(userName)){
            return false;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password)

        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()));
        return encoder.matches(password, userRepository.findByEmail(userName).get().getPassword());*/

        UserDetails user = userDetailsService.loadUserByUsername(userName);

        if (!encoder.matches(password, user.getPassword())) {
            logger.warn("the password is incorrect");
            throw new BadCredentialsException("Неверно указан пароль!");
        }
        logger.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()));
        logger.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        return true;
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        /*userRepository.save(new ru.min.resaleplatform.model.User(registerReq.getUsername(), encoder.encode(registerReq.getPassword()), registerReq.getFirstName(),
                registerReq.getLastName(), registerReq.getPhone(), role));
        return true;*/
        if (userRepository.existsByEmail(registerReq.getUsername())) {
            logger.warn("user already exists");
            throw new ValidationException(String.format("Пользователь \"%s\" уже зарегистрирован!", registerReq.getUsername()));
        }

//        User user = new User(registerReq.getUsername(), encoder.encode(registerReq.getPassword()), registerReq.getFirstName(),
//                registerReq.getLastName(), registerReq.getPhone(), role);
        User user = new User(
                registerReq.getUsername(),
                encoder.encode(registerReq.getPassword()),
                registerReq.getFirstName(),
                registerReq.getLastName(),
                registerReq.getPhone(),
                role
        );
        logger.info("user registered");
        userRepository.save(user);
        return true;
    }
}
