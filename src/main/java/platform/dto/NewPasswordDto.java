package platform.dto;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

@Builder
@Data
@Valid
public class NewPasswordDto {


    private String currentPassword;

    @Length(min = 8)
    private String newPassword;
}
