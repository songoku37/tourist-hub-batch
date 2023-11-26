package com.leesungjae.tourist_hub_batch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class TouristAttraction extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TOURIST_ID")
    private long touristId;

    private String title;
    private String overview;
    private String infoCenterCall;
    private long like;
    private String restDate;
    private String homepage;
    private String addr;
    private String mapLatt;
    private String mapLngt;
    private int areaCode;

    @OneToMany(mappedBy = "touristAttraction", cascade = CascadeType.ALL)
    private List<HashTag> hashTags = new ArrayList<>();

    @OneToMany(mappedBy = "touristAttraction", cascade = CascadeType.ALL)
    private List<TouristImage> touristImages = new ArrayList<>();

    @OneToMany(mappedBy = "touristAttraction", cascade = CascadeType.ALL)
    private  List<Address> addressList = new ArrayList<>();

}
