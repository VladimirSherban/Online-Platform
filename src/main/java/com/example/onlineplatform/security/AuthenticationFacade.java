package com.example.onlineplatform.security;

import com.example.onlineplatform.exception.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;



public interface AuthenticationFacade {
    /**
     * Получние логина пользователя из контекста Spring
     * @return String Login
     * @throws ForbiddenException
     */
    default String getLogin() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            return principal.getUsername();
        } catch (Exception e) {
            throw new ForbiddenException();
        }
    }
}
