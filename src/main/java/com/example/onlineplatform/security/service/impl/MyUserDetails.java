package com.example.onlineplatform.security.service.impl;

import com.example.onlineplatform.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
public class MyUserDetails extends org.springframework.security.core.userdetails.User{
    private final Integer id;

    public MyUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id = user.getId();
    }

    @Override
    public void eraseCredentials() {
    }
}
