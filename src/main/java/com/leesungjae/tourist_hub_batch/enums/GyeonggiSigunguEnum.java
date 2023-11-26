package com.leesungjae.tourist_hub_batch.enums;

import java.util.Arrays;
import java.util.Optional;

public enum GyeonggiSigunguEnum {

    ALL("전체",0),

    GAPYEONGGUN("가평군",1),
    GOYANGSI("고양시",2),
    GWACHEONSI("과천시",3),
    GWANGMYEONGSI("광명시",4),
    GWANGJUSI("광주시",5),
    GURISI("구리시",6),
    GUNPOSI("군포시",7),
    GIMPOSI("김포시",8),
    NAMYANGJUSI("남양주시",9),
    DONGDUCHEONSI("동두천시",10),
    BUCHEONSI("부천시",11),
    SEONGNAMSI("성남시",12),
    SUWONSI("수원시",13),
    SIHEUNGSI("시흥시",14),
    ANSANSI("안산시",15),
    ANSEONGSI("안성시",16),
    ANYANGSI("안양시",17),
    YANGJUSI("양주시",18),
    YANGPYEONGGUN("양평군",19),
    YEOJUSI("여주시",20),
    YEONCHEONGUN("연천군",21),
    OSANSI("오산시",22),
    YONGINSI("용인시",23),
    UIWANGSI("의왕시",24),
    UIJEONGBUSI("의정부시",25),
    ICHUNSI("이천시",26),
    PAJUSI("파주시",27),
    PYEONGTAEKSI("평택시",28),
    POCHONSI("포천시",29),
    HANAMSI("하남시",30),
    HWASEONGSI("화성시",31);

    private final String sigunguName;
    private final int code;

    GyeonggiSigunguEnum(String sigunguName, int code) {
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
                      .map(GyeonggiSigunguEnum::getName);


        return resultOption.orElse(null);

    }
}