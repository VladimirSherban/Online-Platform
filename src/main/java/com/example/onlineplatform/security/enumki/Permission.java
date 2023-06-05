package com.example.onlineplatform.security.enumki;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Права пользователей
 */
@RequiredArgsConstructor
@Getter
public enum Permission {
    USERS_CRUD("users.crud"),
    USERS_FULL("users.full"),
    ADS_CRUD("ads.crud"),
    ADS_FULL("ads.full"),
    ADS_COMMENT_CRUD("ads.comment.crud"),
    ADS_COMMENT_FULL("ads.comment.full");
    private final String VALUE;
}
