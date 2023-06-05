package platform.mapper;

import platform.dto.model_dto.UserDto;
import platform.model.User;

import java.util.UUID;

public interface GenerateLink {
    String PATH = "/users/avatar/";

    default String generateLink(User user) {
        if (user.getAvatar() == null) {
            return null;
        }
        return PATH + user.getAvatar().getUuid() + "/";
    }

    default UUID getUUID(UserDto userDto) {
        String image = userDto.getImage();
        if (image == null) {
            return null;
        }
        int length = PATH.length();
        return UUID.fromString(image.substring(length, image.length() - 1));
    }
}
