package platform.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "user_avatars")
@Data
public class UserAvatar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image")
    private String image;
}
