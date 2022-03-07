package com.dto.book;

public class BookDTO {
    private long bookId;
    private String bookTitle;
    private String publisher;
    private String publishTime;
    private String author;
    private String detail;
    private String thumbnail;

    public BookDTO() {
        //
    }

    public BookDTO(BookApiDTO bookApiDTO) {
        this.bookId = Long.parseLong(bookApiDTO.getIsbn());
        this.bookTitle = bookApiDTO.getTitle();
        this.publisher = bookApiDTO.getPublisher();
        this.publishTime = bookApiDTO.getPubdate();
        this.author = bookApiDTO.getAuthor();
        this.detail = bookApiDTO.getDescription();
        this.thumbnail = bookApiDTO.getImage();
    }

    public long getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getDetail() {
        return detail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "bookId=" + bookId +
                ", bookTitle='" + bookTitle + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", author='" + author + '\'' +
                ", detail='" + detail + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
