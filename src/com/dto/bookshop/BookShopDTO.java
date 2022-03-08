package com.dto.bookshop;

import com.dto.book.BookDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookShopDTO {
    private long bookshopId;
    private long sellerId;
    private String sellerComment;
    private String sellerShort;
    private long bookId;
    private String createdAt;
    private String updatedAt;
    private String endedAt;
    private int sellPrice;
    private BookShopStatusCode sellStatusId;
    private TradeCode selltypeId;
    private CategoryCode category;

    public BookShopDTO() {
        //
    }

    public long getBookshopId() {
        return bookshopId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public String getSellerComment() {
        return sellerComment;
    }

    public String getSellerShort() {
        return sellerShort;
    }

    public long getBookId() {
        return bookId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public BookShopStatusCode getSellStatusId() {
        return sellStatusId;
    }

    public TradeCode getSelltypeId() {
        return selltypeId;
    }

    public CategoryCode getCategory() {
        return category;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "BookShopDTO{" +
                "bookshopId=" + bookshopId +
                ", sellerId=" + sellerId +
                ", sellerComment='" + sellerComment + '\'' +
                ", sellerShort='" + sellerShort + '\'' +
                ", bookId=" + bookId +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", endedAt='" + endedAt + '\'' +
                ", sellPrice=" + sellPrice +
                ", sellStatusId=" + sellStatusId +
                ", selltypeId=" + selltypeId +
                ", category=" + category +
                '}';
    }
}
