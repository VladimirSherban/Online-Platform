package platform.dto.model_dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdsDto {

    private Integer pk;
    private Integer author;
    private Integer price;
    private String title;
    //    private String description;
    private String image;

}
