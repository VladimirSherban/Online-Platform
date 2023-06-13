package platform.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private String createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User commentAuthor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ads_id")
    private Ads ad;
}
