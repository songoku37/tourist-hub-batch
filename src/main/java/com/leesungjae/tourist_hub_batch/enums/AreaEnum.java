package com.leesungjae.tourist_hub_batch.enums;


import java.util.Arrays;
import java.util.Optional;

public enum AreaEnum {

    SEOUL("서울",1),
    INCHEON("인천",2),
    GYEONGGI("경기",31);

    private final String name;
    private final int code;


    AreaEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }


    public static String getByCode(int code) {

        Optional<String> resultOption = Arrays.stream(values())
                .filter(area -> area.getCode() == code)
                .findFirst()
                .map(AreaEnum::getName);


        return resultOption.orElse(null);
    }

}