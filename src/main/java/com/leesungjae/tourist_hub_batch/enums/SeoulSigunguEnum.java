package com.leesungjae.tourist_hub_batch.enums;

import java.util.Arrays;
import java.util.Optional;

public enum SeoulSigunguEnum {

    ALL("전체",0),
    GANGNAMGU("강남구",1),
    GANGDONGGU("강동구",2),
    GANGBUKGU("강북구",3),
    GANGSEOGU("강서구",4),
    GWANAKGU("관악구",5),
    GWANGJINGU("광진구",6),
    GUROGU("구로구",7),
    GEUMCHEONGU("금천구",8),
    NOWONGU("노원구",9),
    DOBONGGU("도봉구",10),
    DONGDAEMUNGU("동대문구",11),
    DONGJAKGU("동작구",12),
    MAPOGU("마포구",13),
    SEODAEMUNGU("서대문구",14),
    SEOCHOGU("서초구",15),
    SEONGDONGGU("성동구",16),
    SEONGBUKGU("성북구",17),
    SONGPAGU("송파구",17),
    YANGCHEONGU("양천구",18),
    YEONGDEUNGPOGU("영등포구",19),
    YONGSANGU("용산구",20),
    EUNPYEONGGU("은평구",21),
    JONGNOGU("종로구",22),
    JUNGGU("중구",23),
    JUNGNANGGU("중랑구",24);

    private final String sigunguName;
    private final int code;

    SeoulSigunguEnum(String sigunguName, int code) {
        this.sigunguName = sigunguName;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.sigunguName;
    }

    public static String getByCode(int code) {
        Optional<String> resultOption =
                Arrays.stream(values())
                        .filter(area -> area.getCode() == code)
                        .findFirst()
                        .map(SeoulSigunguEnum::getName);


        return resultOption.orElse(null);
    }
}