package platform.mapper;

import platform.dto.AdsCreateDto;
import platform.dto.model_dto.AdsDto;
import platform.model.Ads;
import platform.model.Picture;
import platform.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

/**
 * Преобразователь сущьностей
 */
@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "image", expression = "java(getLinkList(ad.getPicture()))")
    AdsDto toDto(Ads ad);

    List<AdsDto> toDtoList(List<Ads> adList);

    @Mapping(target = "title", source = "adCreateDto.title")
    @Mapping(target = "price", source = "adCreateDto.price")
    @Mapping(target = "description", source = "adCreateDto.description")
    Ads updateAds(AdsCreateDto adCreateDto, Ads ad);

    default List<String> getLinkList(Picture picture) {
        String prefix = "/ads/image/";
        return picture != null ? List.of(prefix.concat(picture.getUuid().toString())) : null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "picture", source = "picture")
    @Mapping(target = "description", source = "adCreateDto.description")
    @Mapping(target = "price", source = "adCreateDto.price")
    @Mapping(target = "title", source = "adCreateDto.title")
    Ads buildAd(User user, AdsCreateDto adCreateDto, Picture picture);
}