package com.dto.member;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class MemberDTO {

    private static final String REGEX_NAME = "^[가-힣a-zA-Z0-9_]{2,12}$";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#]{8,20}$";

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

        public SignUp() {
            //
        }

        public SignUp(String name, String password, String email, String address, String addressDetail) {
            validate(name, password, email);
            this.name = name;
            this.password = password;
            this.email = email;
            this.address = address;
            this.addressDetail = addressDetail;
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
    }
    
    public static void validate(String name, String email, String password) {
        validName(name);
        validPassword(password);
        validEmail(email);
    }

    private static void validName(String name) {
        if (!Pattern.matches(REGEX_NAME, name)) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid MemberName: {0}", name));
        }
    }

    private static void validEmail(String email) {
        if (!Pattern.matches(REGEX_EMAIL, email)) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid Email: {0}", email));
        }
    }

    private static void validPassword(String password) {
        if (!Pattern.matches(REGEX_PASSWORD, password)) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid Password: {0}", password));
        }
    }
}
