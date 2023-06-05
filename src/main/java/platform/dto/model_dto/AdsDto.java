package platform.dto.model_dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * Обьект для вывода данных по результатам запросов
 */
@Builder
@Data
public class AdsDto {

    @JsonProperty("pk")
    private Integer pk;

    @JsonProperty("author")
    private Integer author;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("title")
    private String title;

    @JsonProperty("image")
    @Valid
    private List<String> image;
}
