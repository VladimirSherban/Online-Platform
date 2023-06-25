package platform.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import platform.dto.model_dto.CommentDto;
import platform.model.Comment;

@Mapper(componentModel = "spring")
public interface AdCommentMapper{
    @Mapping(target = "commentAuthor", ignore = true)
    @Mapping(target = "id", source="pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ad", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(target = "author", source = "ad.adsAuthor")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "createdAt", source = "entity.createdAt")
//    @Mapping(target = "authorImage", source = "commentAuthor.image")
    @Mapping(target = "authorFirstName", source = "commentAuthor.firstName")
    CommentDto toDto(Comment entity);
}
