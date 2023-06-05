package platform.mapper;

import platform.dto.model_dto.AdsDto;
import platform.dto.response_wrapper.ResponseWrapperAdsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

/**
 * Преобразователь сущьностей
 */
@Mapper(componentModel = "spring")
public interface ResponseWrapperAdMapper {
    @Mapping(source = "count", target = "count")
    @Mapping(source = "ads", target = "results")
    ResponseWrapperAdsDto toDto(Integer count, List<AdsDto> ads);
}
