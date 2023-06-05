package platform.mapper;
import platform.dto.model_dto.CommentDto;
import platform.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "ad", ignore = true)
    @Mapping(source = "author", target ="user.id")
    @Mapping(source = "pk", target ="id")
    Comment toEntity(CommentDto adsComment);

    @Mapping(source = "user.id", target ="author")
    @Mapping(source = "id", target ="pk")
    CommentDto toDto(Comment adComment);

    List<CommentDto> toDtos(List<Comment> commentList);
}
