package platform.dto.response_wrapper;

import platform.dto.model_dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Valid
public class ResponseWrapperCommentDto {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    @Valid
    private List<CommentDto> results;
}
