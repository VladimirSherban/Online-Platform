package platform.dto;

import lombok.Data;
import platform.model.Comment;

import java.util.List;

@Data
public class ResponseWrapperCommentDto {

    private Integer count;
    private List<Comment> results;

}
