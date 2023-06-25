package platform.dto.model_dto;

import lombok.*;

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
