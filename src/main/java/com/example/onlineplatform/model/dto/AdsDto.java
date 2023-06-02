package com.example.onlineplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsDto {

    private Integer author;
    private String image;
    private int pk;
    private int price;
    private String title;
}
