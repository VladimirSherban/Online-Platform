package platform.repository;

import platform.model.User;
import platform.model.UserAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAvatarRepository  extends JpaRepository<UserAvatar, UUID> {
    Optional<UserAvatar> findFirstByUser(User user);
}
