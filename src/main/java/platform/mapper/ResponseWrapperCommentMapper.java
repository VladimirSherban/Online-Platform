package platform.mapper;

import platform.dto.model_dto.CommentDto;
import platform.dto.response_wrapper.ResponseWrapperCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseWrapperCommentMapper {
    @Mapping(source = "count", target ="count")
    @Mapping(source = "adsComment", target ="results")
    ResponseWrapperCommentDto toDto(Integer count, List<CommentDto> adsComment);
}
