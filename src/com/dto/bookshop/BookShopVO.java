package com.dto.bookshop;

import com.dto.book.BookDTO;
import com.dto.member.MemberDTO;

public class BookShopVO {

    public static class Book {
        private BookShopDTO bookShop;
        private BookDTO book;

        public Book() {

        }

        public BookShopDTO getBookShop() {
            return bookShop;
        }

        public BookDTO getBook() {
            return book;
        }
    }

    public static class Member extends Book {
        private MemberDTO.Info member;
        private boolean itIsMe;

        public Member() {

        }

        public MemberDTO.Info getMember() {
            return member;
        }

        public boolean isItIsMe() {
            return itIsMe;
        }

        public void setItIsMe(boolean itIsMe) {
            this.itIsMe = itIsMe;
        }
    }
}
