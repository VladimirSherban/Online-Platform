package com.example.onlineplatform.controller;

import com.example.onlineplatform.model.dto.NewPasswordDto;
import com.example.onlineplatform.model.dto.UserDto;
import com.example.onlineplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> findUserById(){
        return ResponseEntity.ok(userService.findUser());
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto){
        userService.updatePassword(newPasswordDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateImage(@RequestParam(value = "image") MultipartFile multipartFile){
        userService.updateImage(multipartFile);
        return ResponseEntity.ok().build();
    }
}
