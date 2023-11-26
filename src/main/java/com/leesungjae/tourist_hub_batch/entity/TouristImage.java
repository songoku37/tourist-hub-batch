package com.leesungjae.tourist_hub_batch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class TouristImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imageId;

    @ManyToOne // 다대일 (ERD 보고 어떤 관계인지 설정하면 된다)
    @JoinColumn(name = "TOURIST_ID")
    private TouristAttraction touristAttraction;

    private String path;

}
