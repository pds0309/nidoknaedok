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
}
