package com.example.onlineplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsPropertiesDto {

    private String description;
    private int price;
    private String title;
    //private MultipartFile image;
}
