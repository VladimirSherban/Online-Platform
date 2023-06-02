package com.example.onlineplatform.security.service.impl;

import com.example.onlineplatform.model.Ads;
import com.example.onlineplatform.model.Comment;
import com.example.onlineplatform.security.dto.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private SecurityUtils() {
    }

    public static MyUserDetails getUserDetailsFromContext() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getUserIdFromContext() {
        return getUserDetailsFromContext().getId();
    }

    public static void checkPermissionToAds(Ads ads) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != ads.getAdsAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить/удалить объявление, нужно иметь роль ADMIN или быть владельцем этого объявления");
        }
    }

    public static void checkPermissionToAdsComment(Comment adsComment) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != adsComment.getCommentAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить/удалить комментарий, нужно иметь роль ADMIN или быть владельцем этого комментария");
        }
    }
}
