package platform.dto.model_dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Builder
@Data
public class CommentDto {

    @JsonProperty("pk")
    private Integer pk;

    @JsonProperty("author")
    private Integer author;

    @JsonProperty("authorImage")
    private String authorImage;

    @JsonProperty("authorFirstName")
    private String authorFirstName;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("text")
    @NotNull
    private String text;
}
