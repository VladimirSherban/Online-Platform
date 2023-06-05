package platform.mapper;

import platform.dto.RegReqDto;
import platform.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {
    @Mapping(source = "username", target = "email")
    User toEntity(RegReqDto req);
}
