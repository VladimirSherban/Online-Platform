package platform.dto.model_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdsDto {

    private int pk;
    private int author;
    private int price;
    private String title;
    private String description;
    private String image;

}
