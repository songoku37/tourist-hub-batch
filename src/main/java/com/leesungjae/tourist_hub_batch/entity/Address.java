package com.leesungjae.tourist_hub_batch.entity;


import com.leesungjae.tourist_hub_batch.enums.AreaEnum;
import com.leesungjae.tourist_hub_batch.utils.AddressUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Address extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;

    private int areaCode;// 시코드
    private int sigungucodeCode; // 시구군 코드

    private String areaName;
    private String sigungucodeName;

    @ManyToOne // 일대일 (ERD 보고 어떤 관계인지 설정하면 된다)
    @JoinColumn(name = "TOURIST_ID")
    private TouristAttraction touristAttraction;

    public Address(int areaCode, int sigungucodeCode) {
        this.areaCode = areaCode;
        this.sigungucodeCode = sigungucodeCode;
        this.areaName = AreaEnum.getByCode(areaCode);
        this.sigungucodeName = AddressUtils.getsigungucodeName(areaCode, sigungucodeCode);
    }

}
