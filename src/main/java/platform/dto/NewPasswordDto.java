package platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Нужен для контроллера Пользователя - метод /set_password
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NewPasswordDto {
    private String currentPassword;
    private String newPassword;

}
