package com.dto.member;

public enum MemberRole {
    NORMAL("일반 사용자"),
    MANAGER("매니저"),
    BANNED("정지된 사용자"),
    ADMIN("관리자"),
    RESIGN("탈퇴한 사용자");

    private final String detail;

    MemberRole(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
