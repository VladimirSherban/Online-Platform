package platform.security.service;


import platform.dto.RegReqDto;
import platform.security.dto.Role;

public interface AuthService {

    boolean login(String userName, String password) throws Exception;

    boolean register(RegReqDto regReqDto, Role role);
}
