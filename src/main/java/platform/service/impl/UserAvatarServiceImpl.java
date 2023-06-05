package platform.service.impl;

import platform.exception.AvatarNotFoundException;
import platform.exception.CustomIOException;
import platform.exception.UserNotFoundException;
import platform.model.User;
import platform.model.UserAvatar;
import platform.repository.UserAvatarRepository;
import platform.repository.UserRepository;
import platform.service.UserAvatarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

/**
 * Сервис для загрузки, обновления аватара пользователя для UserController
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserAvatarServiceImpl implements UserAvatarService {
    private final UserAvatarRepository userAvatarRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public String save(MultipartFile image, String login) {
        String encodedString;
        User user = userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
        UserAvatar avatar = userAvatarRepository.findFirstByUser(user).orElseGet(UserAvatar::new);
        try {
            byte[] fileContent = image.getBytes();
            encodedString = Base64.getEncoder().encodeToString(fileContent);
            avatar.setUser(user);
            avatar.setImage(encodedString);
            userAvatarRepository.save(avatar);
            user.setAvatar(avatar);
            userRepository.save(user);
            return avatar.getUuid().toString();
        } catch (IOException e) {
            log.warn("Error uploading an avatar from user :: {}", user.getEmail());
            throw new CustomIOException();
        }
    }

    @Override
    public byte[] download(UUID uuid) {
        UserAvatar avatar = userAvatarRepository.findById(uuid).orElseThrow(() -> new AvatarNotFoundException(uuid));
        return Base64.getDecoder().decode(avatar.getImage());
    }
}
