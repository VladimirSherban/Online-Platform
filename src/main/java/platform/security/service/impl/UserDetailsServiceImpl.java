package platform.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import platform.model.User;
import platform.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByName(username);
        return new MyUserDetails(user);
    }

    private User getUserByName(String username) {
        return userRepository.findFirstByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь по email не найден"));
    }


    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = getUserByName(userDetails.getUsername());
        user.setPassword(newPassword);
        MyUserDetails updateUserDetalis = new MyUserDetails(userRepository.save(user));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updateUserDetalis,
                        null,
                        updateUserDetalis.getAuthorities())
        );
        return updateUserDetalis;
    }
}
