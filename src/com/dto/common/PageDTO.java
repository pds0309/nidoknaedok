package com.dto.common;

import java.util.List;

public class PageDTO<T> {
    private int currentPage;
    private int perPage;
    private int totalCount;
    private List<T> tList;

    public PageDTO(int currentPage, int perPage, int totalCount, List<T> tList) {
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.totalCount = totalCount;
        this.tList = tList;
    }

    public PageDTO(int currentPage, int perPage, List<T> tList) {
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.tList = tList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotalCount() {
        return totalCount;

    }

    public List<T> gettList() {
        return tList;
    }
}
