package com.dto.bookshop;

public enum TradeCode {
    RS(100, "판매요청"),
    SB(200, "대여"),
    RB(300, "대여요청"),
    SS(400, "판매");

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
