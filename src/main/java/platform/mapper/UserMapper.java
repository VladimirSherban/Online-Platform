package platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import platform.dto.CreateUserDto;
import platform.dto.RegReqDto;
import platform.dto.model_dto.UserDto;
import platform.model.Image;
import platform.model.User;


@Mapper(componentModel = "spring")
public interface UserMapper extends MapperSchema<UserDto, User> {

    CreateUserDto toCreateUserDto(User entity);

    User createUserDtoToEntity(CreateUserDto dto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "role", defaultValue = "USER")
    User toEntity(RegReqDto dto);


    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto dto);

    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    UserDto toDto(User entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return "";
        }
        return "/users/image/" + image.getId();

    }

}