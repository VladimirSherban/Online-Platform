package platform.dto.model_dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto  {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String image;
}
