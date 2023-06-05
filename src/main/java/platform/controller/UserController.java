package platform.controller;

import platform.api.UsersApi;
import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.dto.response_wrapper.ResponseWrapperUserDto;
import platform.security.AuthenticationFacade;
import platform.security.service.PermissionService;
import platform.service.UserAvatarService;
import platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class UserController implements UsersApi, AuthenticationFacade {
    private final UserService userService;
    private final UserAvatarService avatarService;
    private final PermissionService permissionService;

    @Value("${platform.avatar.size}")
    private Long MAX_AVATAR_SIZE;

    @Override
    @PreAuthorize("hasAnyAuthority('users.crud')")
    public ResponseEntity<UserDto> getUserUsingGET(Integer id) {
        permissionService.checkPermissionForUserController(getLogin(), id);
        return ResponseEntity.ok(userService.findUser(id));
    }

    @Override
    @PreAuthorize("hasAuthority('users.crud')")
    public ResponseEntity<UserDto> getUserMeUsingGET() {
        return ResponseEntity.ok(userService.findUser(getLogin()));
    }

    @Override
    @PreAuthorize("hasAuthority('users.full')")
    public ResponseEntity<ResponseWrapperUserDto> getUsersUsingGET() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @Override
    @PreAuthorize("hasAuthority('users.crud')")
    public ResponseEntity<NewPasswordDto> setPasswordUsingPOST(NewPasswordDto newPasswordDto) {
        return ResponseEntity.ok(userService.setPassword(newPasswordDto, getLogin()));
    }

    @Override
    @PreAuthorize("hasAuthority('users.crud')")
    public ResponseEntity<UserDto> updateUserUsingPATCH(UserDto userDto) {
        String login = getLogin();
        userDto.setEmail(login);
        // разрешаем обновлять только себя. Если нужен функционал, обновления профиля пользователя админом. Можем сделать отдельный эндпоинт
        permissionService.checkPermissionForUserController(login, userDto);
        UserDto user = userService.findUser(login);
        userDto.setImage(user.getImage()); // Для обновления аватарки есть отдельгный эндпоинт. Не даем обновлять аватарку таким образом
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @Override
    @PreAuthorize("hasAuthority('users.crud')")
    public ResponseEntity<String> updateUserAvatar(MultipartFile image) {
        if (image.getSize() > MAX_AVATAR_SIZE) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("The teapot cannot accommodate such a size");
        }
        return ResponseEntity.ok(avatarService.save(image, getLogin()));
    }

    @Override
    public byte[] getImage(UUID uuid) {
        return avatarService.download(uuid);
    }
}
