package com.dto.member;

import com.utils.RegexValidator;

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
        private MemberRole authority;

        public SignUp() {
            //
        }

        public SignUp(String name, String password, String email, String address, String addressDetail) {
            RegexValidator.validate(name, email, password);
            this.name = name;
            this.password = password;
            this.email = email;
            this.address = address;
            this.addressDetail = addressDetail;
            this.authority = MemberRole.NORMAL;
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

        public MemberRole getAuthority() {
            return authority;
        }
    }

    /**
     * 멤버 조회에 대한 모델
     * 다양한 형태일 수 있다.
     */
    public static class Info {
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

        public Info() {
            //
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
    }
}
