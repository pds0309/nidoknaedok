package com.dto.bookshop;

import com.dto.book.BookDTO;
import com.dto.member.MemberDTO;
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
    private BookDTO book;
    private String createdAt;
    private String updatedAt;
    private String endedAt;
    private long sellStatusId;
    private String sellerImage;
    private int sellPrice;
    private long selltypeId;
    private String category;

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

    public BookDTO getBook() {
        return book;
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

    public long getSellStatusId() {
        return sellStatusId;
    }

    public String getSellerImage() {
        return sellerImage;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public long getSelltypeId() {
        return selltypeId;
    }

    public String getCategory() {
        return category;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public void setSellerId(long id) {
        this.sellerId = id;
    }

    @Override
    public String toString() {
        return "BookShopDTO{" +
                "bookshopId=" + bookshopId +
                ", sellerId=" + sellerId +
                ", sellerComment='" + sellerComment + '\'' +
                ", sellerShort='" + sellerShort + '\'' +
                ", book=" + book +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", endedAt='" + endedAt + '\'' +
                ", sellStatusId=" + sellStatusId +
                ", sellerImage='" + sellerImage + '\'' +
                ", sellPrice=" + sellPrice +
                ", sellTypeId=" + selltypeId +
                ", category='" + category + '\'' +
                '}';
    }
}
