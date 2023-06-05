package platform.dto.model_dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Builder
@Data
public class CommentDto {

    @JsonProperty("pk")
    private Integer pk;

    @JsonProperty("author")
    private Integer author;

    @JsonProperty("createdAt")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;

    @JsonProperty("text")
    @NotNull
    private String text;
}
