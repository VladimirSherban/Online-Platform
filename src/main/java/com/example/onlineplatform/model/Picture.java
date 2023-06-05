package com.example.onlineplatform.model;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ads_pictures")
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "uuid")
    private UUID uuid;
    @Lob
    @Column(name = "file_bytes")
    private byte[] data;
    @Column(name = "file_name")
    private String fileName;
}
