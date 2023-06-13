package platform.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "title")
    private String title;

    @OneToOne
    @Column(name = "image")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User adsAuthor;

}