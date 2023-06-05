package com.example.onlineplatform.repository;

import com.example.onlineplatform.model.User;
import com.example.onlineplatform.model.UserAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAvatarRepository  extends JpaRepository<UserAvatar, UUID> {
    Optional<UserAvatar> findFirstByUser(User user);
}
