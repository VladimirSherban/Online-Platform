package platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import platform.dto.model_dto.CommentDto;
import platform.model.Comment;

@Mapper
public interface AdCommentMapper  extends MapperSchema<CommentDto, Comment>{
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source="pk")
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "authorImage", source = "image")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    CommentDto toDto(Comment entity);
}
