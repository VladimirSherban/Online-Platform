package platform.dto;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Builder
@Data
@Valid
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    @Email
    private String email;
}
