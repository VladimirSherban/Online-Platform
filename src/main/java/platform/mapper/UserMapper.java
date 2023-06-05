package platform.mapper;

import platform.dto.model_dto.UserDto;
import platform.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenerateLink {
    @Mapping(target = "image", expression = "java(generateLink(user))")
    UserDto toDto(User user);

    @Mapping(target = "avatar.uuid", expression = "java(getUUID(userDto))")
    User toEntity(UserDto userDto);

    List<UserDto> toDtos(List<User> users);
}
