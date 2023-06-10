package platform.security.service;


import platform.security.dto.RegisterReq;
import platform.security.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
