package com.dto.bookshop;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CategoryCode {

    C01("문학,인물"),
    C02("유아,아동"),
    C03("소설,수필"),
    C04("교육,전문"),
    C05("역사,문화"),
    C06("철학,심리"),
    C08("만화,오락"),
    C09("영화,음반"),
    C10("총류,전집");

    private final String name;

    CategoryCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CategoryCode lookup(String name) {
        for (CategoryCode element : CategoryCode.values()) {
            if (element.name.equals(name)) {
                return element;
            }
        }
        return null;
    }
}
