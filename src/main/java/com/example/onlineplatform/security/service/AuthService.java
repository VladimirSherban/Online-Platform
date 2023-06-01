package com.example.onlineplatform.security.service;

import com.example.onlineplatform.security.dto.RegisterReq;
import com.example.onlineplatform.security.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
