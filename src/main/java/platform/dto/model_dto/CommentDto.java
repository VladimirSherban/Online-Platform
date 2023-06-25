package platform.dto.model_dto;

import lombok.*;
import platform.model.User;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private int pk;
    private User author;
    private String authorImage;
    private String authorFirstName;
    private LocalDateTime createdAt;
    private String text;

    public CommentDto(int pk, LocalDateTime createdAt, String text) {
        this.pk = pk;
        this.createdAt = createdAt;
        this.text = text;
    }
}
