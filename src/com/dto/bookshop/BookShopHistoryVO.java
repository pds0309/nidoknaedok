package com.dto.bookshop;

import com.dto.member.MemberDTO;

public class BookShopHistoryVO {

    public static class Member {
        private BookShopHistoryDTO history;
        private MemberDTO.Info member;

        public Member() {
            //
        }

        public BookShopHistoryDTO getHistory() {
            return history;
        }

        public MemberDTO.Info getMember() {
            return member;
        }
    }
}
