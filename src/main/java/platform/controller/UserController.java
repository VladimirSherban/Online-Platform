package platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.mapper.UserMapper;
import platform.model.User;
import platform.repository.UserRepository;
import platform.security.dto.Role;
import platform.service.ImageService;
import platform.service.UserService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Operation(summary = "Поиск пользователя по id",
            operationId = "getUser",
            responses = {@ApiResponse
                    (responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.ALL_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "USER"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int id) {
       // return ResponseEntity.ok(userRepository.findById(id));
        return ResponseEntity.ok(userMapper.toDto(userService.getUserById(id)));
    }

    @GetMapping("/me")
    public UserDto getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.toDto(userService.getUsers(authentication.getName()));
    }

    @Operation(summary = "Смена пароля Пользователя",
            operationId = "updatePassword",
            responses = {@ApiResponse
                    (responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "USER"
    )
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> updatePassword(@RequestBody NewPasswordDto newPasswordDto) {
        userService.updatePassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
        return ResponseEntity.ok(newPasswordDto);
    }

    @Operation(summary = "Обновить картинку у Пользователя",
            responses = {@ApiResponse
                    (responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
                            )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "USER"
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestParam MultipartFile image) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(userService.updateUserImage(image, authentication.getName()));
    }


    @Operation(summary = "Редактировать пользователя", responses = {@ApiResponse
            (responseCode = "200",
                    description = "OK"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found"
            )
    }, tags = "USER")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(
                userMapper.toDto(userService.updateUser(userDto, authentication.getName())));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/updateRole")
    public ResponseEntity<UserDto> updateRole(@PathVariable("id") long id, Role role) {
        UserDto userDto = userMapper.toDto(userService.updateRole(id, role));
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Обновить картинку польпозателя", operationId = "UpdateImageById", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Image is updated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found"
            )}, tags = "USER")
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(imageService.getImageById(id).getImage());
    }


}
