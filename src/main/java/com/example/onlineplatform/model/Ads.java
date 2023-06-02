package com.example.onlineplatform.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private int price;
    private String title;
    private String image;
    @ManyToOne
    private User adsAuthor;

    public Ads(String description, int price, String title, String image) {
        this.description = description;
        this.price = price;
        this.title = title;
        this.image = image;
    }

    public Ads(String description, int price, String title) {
        this.description = description;
        this.price = price;
        this.title = title;
    }
}
