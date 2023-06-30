package platform.security.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import platform.model.Ads;
import platform.model.Comment;
import platform.model.User;
import platform.repository.UserRepository;
import platform.security.dto.Role;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecurityUtils {

    private final UserRepository repository;

    public MyUserDetails getUserDetailsFromContext() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void checkPermissionToAds(Ads ads) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        Optional<User> currentUser = repository.findById(userDetails.getId());

        if (currentUser.isEmpty() || !currentUser.get().getRole().equals(Role.ADMIN)
                && !Objects.equals(userDetails.getId(), ads.getAdsAuthor().getId())) {
            throw new AccessDeniedException("No access rights only Owner or Admin");
        }
    }

    public void checkPermissionToComment(Comment comment) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        Optional<User> currentUser = repository.findById(userDetails.getId());

        if (currentUser.isEmpty() || !userDetails.getAuthorities().contains(Role.ADMIN)
                && !Objects.equals(userDetails.getId(), comment.getCommentAuthor().getId())) {
            throw new AccessDeniedException("No access rights only Owner or Admin");
        }
    }
}
