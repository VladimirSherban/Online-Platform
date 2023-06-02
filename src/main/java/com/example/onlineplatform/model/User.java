package com.example.onlineplatform.model;

import com.example.onlineplatform.security.dto.Role;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String image;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email, String password, String firstName, String lastName, String phone, String image) {
        this.email = email;
        this.password = password;
        this.firstname = firstName;
        this.lastname = lastName;
        this.phone = phone;
        this.image = image;
    }

    public User(String email, String password, String firstName, String lastName, String phone) {
        this.email = email;
        this.password = password;
        this.firstname = firstName;
        this.lastname = lastName;
        this.phone = phone;
    }

    public User(String email, String password, String firstName, String lastName, String phone, Role role) {
        this.email = email;
        this.password = password;
        this.firstname = firstName;
        this.lastname = lastName;
        this.phone = phone;
        this.role = role;
    }

}
