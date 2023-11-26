package com.leesungjae.tourist_hub_batch.utils;


import com.leesungjae.tourist_hub_batch.enums.GyeonggiSigunguEnum;
import com.leesungjae.tourist_hub_batch.enums.IncheonSigunguEnum;
import com.leesungjae.tourist_hub_batch.enums.SeoulSigunguEnum;

public class AddressUtils {
    public static String getsigungucodeName(int areaCode, int sigungucodeCode) {

        String result;

        switch (areaCode) {
            case 1 -> result = SeoulSigunguEnum.getByCode(sigungucodeCode);
            case 2 -> result = IncheonSigunguEnum.getByCode(sigungucodeCode);
            case 31 -> result = GyeonggiSigunguEnum.getByCode(sigungucodeCode);
            default -> result = "";
        }

        return result;
    }
}

