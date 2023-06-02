package com.example.onlineplatform.service.impl;

import com.example.onlineplatform.model.User;
import com.example.onlineplatform.service.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validate(User user) {
        return true;
    }
}
