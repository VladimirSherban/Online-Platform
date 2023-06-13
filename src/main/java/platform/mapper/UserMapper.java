package platform.mapper;

import org.modelmapper.PropertyMap;
import platform.dto.model_dto.UserDto;
import platform.model.User;

public class UserMapper extends PropertyMap<UserDto, User> {

    @Override
    protected void configure() {
        map().setFirstName(source.getFirstName());
        map().setLastName(source.getLastName());
        map().setPhone(source.getPhone());
        map().setEmail(destination.getEmail());
        map().setRole(destination.getRole());
        map().setImage(destination.getImage());
        map().setPassword(destination.getPassword());
        map().setId(destination.getId());
    }
}
