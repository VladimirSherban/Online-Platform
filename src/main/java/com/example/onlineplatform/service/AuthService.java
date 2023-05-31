package com.example.onlineplatform.service;

import com.example.onlineplatform.dto.RegisterReq;
import com.example.onlineplatform.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
