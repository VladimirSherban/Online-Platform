package com.example.onlineplatform.security.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

}
