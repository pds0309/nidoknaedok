package com.dto.bookshop;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TradeCode {
    SB(200, "대여등록"),
    RB(300, "대여요청");

    private final int selltypeId;
    private final String selltypeDetail;

    TradeCode(int selltypeId, String selltypeDetail) {
        this.selltypeId = selltypeId;
        this.selltypeDetail = selltypeDetail;
    }

    public int getSelltypeId() {
        return selltypeId;
    }

    public String getSelltypeDetail() {
        return selltypeDetail;
    }

    public static TradeCode lookup(int selltypeId) {
        for (TradeCode element : TradeCode.values()) {
            if (element.selltypeId == selltypeId) {
                return element;
            }
        }
        return null;
    }

}
