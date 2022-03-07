package com.dto.bookshop;

public enum BookQueryType {
    TITLE("d_titl"),
    AUTHOR("d_auth"),
    PUBLISHER("d_publ"),
    ISBN("d_isbn");

    private final String query;

    BookQueryType(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
