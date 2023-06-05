package platform.dto.response_wrapper;
import platform.dto.model_dto.AdsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * Обёртка для обьявлений
 */
@Builder
@Data
@Valid
public class ResponseWrapperAdsDto {

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    private List<AdsDto> results;
}
