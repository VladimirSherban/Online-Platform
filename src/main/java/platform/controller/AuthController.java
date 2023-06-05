package platform.controller;


import platform.api.AuthApi;
import platform.dto.LoginReqDto;
import platform.dto.RegReqDto;
import platform.security.AuthenticationFacade;
import platform.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi, AuthenticationFacade {

    private final AuthService authService;

    @Override
    public ResponseEntity<Authentication> loginUsingPOST(LoginReqDto req) {
        try {
            if (authService.login(req.getUsername(), req.getPassword())) {
                return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
            }
        } catch (BadCredentialsException e) {
            log.info("AuthController {}, data: {}", e.getMessage(), req);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> registerUsingPOST(RegReqDto req) {
        boolean register = authService.register(req);
        if (register) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
