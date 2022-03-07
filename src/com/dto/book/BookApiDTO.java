package com.dto.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookApiDTO {
    private String title;
    private String image;
    private String author;
    private String publisher;
    private String isbn;
    private String pubdate;
    private String description;

    public BookApiDTO() {
        //
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        String[] isbns = isbn.split(" ");
        if (isbns.length == 2 && isbns[1].length() == 13) {
            return isbns[1];
        }
        return isbns[0];
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "BookApiDTO{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
