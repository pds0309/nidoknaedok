package com.dto.bookshop;

public class SellTypeDTO {
    private long selltypeId;
    private String selltypeName;
    private long managerId;
    private String selltypeDetail;

    public SellTypeDTO() {
        //
    }

    public long getSelltypeId() {
        return selltypeId;
    }

    public String getSelltypeName() {
        return selltypeName;
    }

    public long getManagerId() {
        return managerId;
    }

    public String getSelltypeDetail() {
        return selltypeDetail;
    }

    @Override
    public String toString() {
        return "SellTypeDTO{" +
                "selltypeId=" + selltypeId +
                ", selltypeName='" + selltypeName + '\'' +
                ", managerId=" + managerId +
                ", selltypeDetail='" + selltypeDetail + '\'' +
                '}';
    }
}
