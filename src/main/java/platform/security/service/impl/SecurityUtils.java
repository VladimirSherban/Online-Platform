package platform.security.service.impl;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import platform.model.Ads;
import platform.model.Comment;
import platform.security.dto.Role;
import platform.security.service.impl.MyUserDetails;

public class SecurityUtils {

    public SecurityUtils() {
    }

    public static MyUserDetails getUserDetailsFromContext() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public static void checkPermissionToAds(Ads ads) {
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getAuthorities().contains(Role.ADMIN)
                && userDetails.getId() != ads.getAdsAuthor().getId()) {
            throw new AccessDeniedException("No access rights only Owner or Admin");
        }
    }

    public static void checkPermissionToComment(Comment comment){
        MyUserDetails userDetails = getUserDetailsFromContext();

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != comment.getCommentAuthor().getId()){
            throw new AccessDeniedException("No access rights only Owner or Admin");
        }
    }
}
