package platform.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Обьект для передачи данных для поиска обьявлений
 */
@Data
@Builder
public class SearchQuery {

    private Integer minPrise;
    private Integer maxPrise;
    private String title;
    private String description;

}
