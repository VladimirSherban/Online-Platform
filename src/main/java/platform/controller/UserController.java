package platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import platform.dto.NewPasswordDto;
import platform.dto.model_dto.UserDto;
import platform.service.UserService;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/us")
    public ResponseEntity<UserDto> findUserById(){
        return ResponseEntity.ok(userService.findUser());
    }

    @PostMapping("/update_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto){
        userService.updatePassword(newPasswordDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/us")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @PatchMapping("/us/update_image")
    public ResponseEntity<Void> updateImage(@RequestParam(value = "image") MultipartFile multipartFile) throws IOException {
        userService.updateImage(multipartFile);
        return ResponseEntity.ok().build();
    }


}
