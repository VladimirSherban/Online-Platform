package platform.model;

import lombok.*;
import platform.security.dto.Role;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_account")
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "image")
    private String image;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


}
