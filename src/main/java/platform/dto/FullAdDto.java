package platform.dto;

import lombok.Data;

@Data
public class FullAdDto {

    private String authorLastName;
    private String authorFirstName;
    private String phone;

    private int price;
    private int pk;

    private String description;
    private String title;

    private String email;

}
