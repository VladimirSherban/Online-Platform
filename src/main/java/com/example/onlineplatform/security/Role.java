package com.example.onlineplatform.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.onlineplatform.security.Permission.*;

/**
 * Роли для пользователей. Состоят из набора прав
 */
@RequiredArgsConstructor
public enum Role {
    ROLE_USER(Set.of(
            USERS_CRUD,
            ADS_CRUD,
            ADS_COMMENT_CRUD)
    ),
    ROLE_ADMIN(Set.of(
            ADS_CRUD, ADS_FULL,
            ADS_COMMENT_CRUD, ADS_COMMENT_FULL,
            USERS_CRUD, USERS_FULL
    ));
    private final Set<Permission> permissions;

    /**
     * Получние набора прав
     * @return Set<SimpleGrantedAuthority>
     */
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getVALUE()))
                .collect(Collectors.toSet());
    }
}
