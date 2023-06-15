package platform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.bind.annotation.RequestMapping;
import platform.dto.AdCreateDto;
import platform.dto.FullAdDto;
import platform.dto.model_dto.AdsDto;
import platform.model.Ads;
import platform.model.Image;

@Mapper
public interface AdMapper extends MapperSchema<AdsDto, Ads>{

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "image", ignore = true)
    Ads toEntity(AdsDto dto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    AdsDto toDto(Ads entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads toEntity(AdCreateDto dto);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    @Mapping(target = "pk", source = "id")
    FullAdDto toFullAdsDto(Ads entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return null;
        }
        return "/ads/image/" +image.getId();
    }
}
