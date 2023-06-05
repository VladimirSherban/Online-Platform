package platform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Builder
@Data
public class LoginReqDto {

    @JsonProperty("username")
    @Email
    private String username;

    @JsonProperty("password")
    private String password;
}
