package platform.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import platform.dto.AdCreateDto;
import platform.dto.FullAdDto;
import platform.dto.model_dto.AdsDto;
import platform.model.Ads;
import platform.model.Image;
import platform.model.User;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "adsAuthor", source = "author", qualifiedByName = "userFromId")
    @Mapping(target = "image", ignore = true)
    Ads toEntity(AdsDto dto);

    @Named("userFromId")
    default User userFromId(int authorId) {
        User user = new User();
        user.setId(authorId);
        return user;
    }


    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "adsAuthor.id")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
//    @Mapping(target = "description", ignore = true)
    AdsDto toDto(Ads entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adsAuthor", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads toEntity(AdCreateDto dto);

    @Mapping(target = "authorFirstName", source = "adsAuthor.firstName")
    @Mapping(target = "authorLastName", source = "adsAuthor.lastName")
    @Mapping(target = "phone", source = "adsAuthor.phone")
    @Mapping(target = "email", source = "adsAuthor.email")
//    @Mapping(target = "image", source = "adsAuthor.image", qualifiedByName = "imageMapping")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    @Mapping(target = "pk", source = "id")
    FullAdDto toFullAdsDto(Ads entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return null;
        }
        return "/ads/image/" + image.getId();
    }
}