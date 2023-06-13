package platform.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity
@Table(name = "image")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "data_image")
    @Lob
    @Type(type = "binary")
    private byte[] image;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "media_type")
    private String mediaType;


    public String toString() {
        return "AdsEntity(id=" + this.getId() + ", image=" + java.util.Arrays.toString(this.getImage()) + ")";
    }
}
