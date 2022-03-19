package com.dto.bookshop;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BookShopStatusCode {
    SUBMIT(1000, "등록됨"),
    PROCESSING(2000, "거래 진행중"),
    WAITING(2500, "완료 대기중"),
    COMMIT(4000, "거래 완료"),
    LOANING(3000, "대여중");

    private final long bookStatusId;
    private final String bookStatusDetail;

    BookShopStatusCode(long bookStatusId, String bookStatusDetail) {
        this.bookStatusId = bookStatusId;
        this.bookStatusDetail = bookStatusDetail;
    }

    public long getBookStatusId() {
        return bookStatusId;
    }

    public String getBookStatusDetail() {
        return bookStatusDetail;
    }

    public static BookShopStatusCode lookup(long code) {
        for (BookShopStatusCode element : BookShopStatusCode.values()) {
            if (element.bookStatusId == code) {
                return element;
            }
        }
        return null;
    }
}
