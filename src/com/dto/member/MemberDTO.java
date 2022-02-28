package com.dto.member;

import com.utils.RegexValidator;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

public class MemberDTO {

    private MemberDTO() {
        //
    }

    /**
     * 멤버 가입 모델
     */
    public static class SignUp {

        private String name;
        private String password;
        private String email;
        private String address;
        private String addressDetail;
        private SocialType socialType;
        private long socialId;

        public SignUp() {
            //
        }

        public SignUp(Builder builder) {
            this.name = builder.name;
            this.password = builder.password;
            this.email = builder.email;
            this.address = builder.address;
            this.addressDetail = builder.addressDetail;
            this.socialType = builder.socialType;
            this.socialId = builder.socialId;
        }

        public static class Builder {
            private final String name;
            private final String password;
            private String email;
            private final String address;
            private String addressDetail = "";
            private SocialType socialType = SocialType.NORMAL;
            private long socialId = -1;

            public Builder(String name, String password, String address) {
                this.name = name;
                this.password = password;
                this.address = address;
            }

            public Builder email(String value) {
                this.email = value;
                return this;
            }

            public Builder addressDetail(String value) {
                this.addressDetail = value;
                return this;
            }

            public Builder socialType(SocialType value) {
                this.socialType = value;
                return this;
            }

            public Builder socialId(long value) {
                this.socialId = value;
                return this;
            }

            public SignUp build() {
                return new SignUp(this);
            }
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public SocialType getSocialType() {
            return socialType;
        }

        public long getSocialId() {
            return socialId;
        }
    }

    /**
     * 멤버 조회에 대한 모델
     */
    public static class Info implements Serializable {

        private long id;
        private String name;
        private String email;
        private String address;
        private String addressDetail;
        private String createdAt;
        private MemberRole authority;
        private int point;
        private String profileImage;
        private String introduction;
        private SocialType socialType;
        private long socialId;

        public Info() {
            //
        }

        public void addSession(HttpSession session) {
            session.setAttribute("meminfo", this);
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public MemberRole getAuthority() {
            return authority;
        }

        public int getPoint() {
            return point;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getIntroduction() {
            return introduction;
        }

        public SocialType getSocialType() {
            return socialType;
        }

        public long getSocialId() {
            return socialId;
        }
    }
}
