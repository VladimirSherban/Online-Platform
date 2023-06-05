package platform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Обьект для создания и обновления обьявлений
 */
@Data
@Builder
public class AdsCreateDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("title")
    private String title;
}