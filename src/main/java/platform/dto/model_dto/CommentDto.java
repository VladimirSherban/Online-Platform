package platform.dto.model_dto;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private int pk;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private String text;

    public CommentDto(int pk, long createdAt, String text) {
        this.pk = pk;
        this.createdAt = createdAt;
        this.text = text;
    }
}
