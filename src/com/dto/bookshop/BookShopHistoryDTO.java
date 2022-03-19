package com.dto.bookshop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookShopHistoryDTO {
    private long historyId;
    private long bookshopId;
    private long memberId;
    private String memo;
    private String createdAt;
    private BookShopStatusCode statusId;

    public BookShopHistoryDTO() {
        //
    }

    public BookShopHistoryDTO(Builder builder) {
        this.bookshopId = builder.bookshopId;
        this.memberId = builder.memberId;
        this.memo = builder.memo;
        this.statusId = builder.statusId;
    }

    public static class Builder {
        private long bookshopId;
        private long memberId;
        private String memo;
        private BookShopStatusCode statusId;

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

        public Builder statusId(BookShopStatusCode value) {
            statusId = value;
            return this;
        }

        public BookShopHistoryDTO build() {
            return new BookShopHistoryDTO(this);
        }
    }

    public long getBookshopId() {
        return bookshopId;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getMemo() {
        return memo.length() > 100 ? memo.substring(0, 100) : memo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public BookShopStatusCode getStatusId() {
        return statusId;
    }

    public long getHistoryId() {
        return historyId;
    }
}
