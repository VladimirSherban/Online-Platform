package platform.dto;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;

@Builder
@Data
@Valid
public class NewPasswordDto {


    private String currentPassword;
    private String newPassword;
}
