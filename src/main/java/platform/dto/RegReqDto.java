package platform.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Builder
@Data
@Valid
public class RegReqDto {

    @Email
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;

}
