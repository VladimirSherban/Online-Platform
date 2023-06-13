package platform.security.service.impl;

import lombok.Getter;
import platform.model.User;

import java.util.List;

@Getter
public class MyUserDetails extends org.springframework.security.core.userdetails.User {
    private final Integer id;

    public MyUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id = user.getId();
    }
}
