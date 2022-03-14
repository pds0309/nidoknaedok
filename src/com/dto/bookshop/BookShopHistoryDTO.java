package com.dto.bookshop;

public class BookShopHistoryDTO {
    private long historyId;
    private long bookshopId;
    private long memberId;
    private String memo;
    private String createdAt;

    public BookShopHistoryDTO() {
        //
    }

    public BookShopHistoryDTO(Builder builder) {
        this.bookshopId = builder.bookshopId;
        this.memberId = builder.memberId;
        this.memo = builder.memo;
    }

    public static class Builder {
        private long bookshopId;
        private long memberId;
        private String memo;

        public Builder() {
            //
        }

        public Builder bookshopId(long value) {
            bookshopId = value;
            return this;
        }

        public Builder memberId(long value) {
            memberId = value;
            return this;
        }

        public Builder memo(String value) {
            memo = value;
            return this;
        }

        public BookShopHistoryDTO build() {
            return new BookShopHistoryDTO(this);
        }
    }

    public long getHistoryId() {
        return historyId;
    }

    public long getBookshopId() {
        return bookshopId;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getMemo() {
        return memo;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
