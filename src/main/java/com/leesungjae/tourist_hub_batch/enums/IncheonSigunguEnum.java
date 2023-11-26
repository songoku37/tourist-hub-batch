package com.leesungjae.tourist_hub_batch.enums;

import java.util.Arrays;
import java.util.Optional;

public enum IncheonSigunguEnum {

    ALL("전체",0),
    GANGHWAGUN("강화군",1),
    GYEYANGGU("계양구",2),
    NAMDONGGU("남동구",3),
    DONGGU("동구",4),
    MICHEONHOLGU("미추홀구",5),
    BUPYEONGGU("부평구",6),
    SEOGU("서구",7),
    YEONSUGU("연수구",8),
    ONGJINGUN("옹진군",9),
    JUNGGU("중구",10);

//    IncheonsigungucodeEnum(String sigunguName, int code) {
//    }

    private final String sigunguName;
    private final int code;

    IncheonSigunguEnum(String sigunguName, int code) {
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
                      .map(IncheonSigunguEnum::getName);


        return resultOption.orElse(null);

    }
}