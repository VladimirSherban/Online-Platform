package platform.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Builder
@Data
@Valid
public class RegReqDto {

    @Email
    private String username;
    @Length(min = 8)
    private String password;
    private String firstName;
    private String lastName;
    private String phone;

}
