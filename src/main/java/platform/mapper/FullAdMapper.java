package platform.mapper;

import platform.dto.FullAdsDto;
import platform.model.Ads;
import platform.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.ArrayList;
import java.util.List;

    /**
     * Преобразователь сущьностей
     */

    @Mapper(componentModel = "spring")
    public interface FullAdMapper {
        @Mapping(target = "pk", source = "ad.id")
        @Mapping(target = "authorFirstName", source = "user.firstName")
        @Mapping(target = "authorLastName", source = "user.lastName")
        @Mapping(target = "phone", source = "user.phone")
        @Mapping(target = "email", source = "user.email")
        @Mapping(target = "title", source = "ad.title")
        @Mapping(target = "price", source = "ad.price")
        @Mapping(target = "description", source = "ad.description")
        @Mapping(target = "images", expression = "java(toList(ad))")
        FullAdsDto toDto(User user, Ads ad);

        default List<String> toList(Ads ad) {
            String prefix = "/ads/image/";
            return ad != null &&
                    ad.getPicture() != null &&
                    ad.getPicture().getUuid() != null ? List.of(prefix.concat(ad.getPicture()
                    .getUuid()
                    .toString())) : new ArrayList<>();
        }

    }
