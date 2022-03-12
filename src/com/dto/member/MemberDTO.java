package com.dto.member;

import com.errors.exception.InvalidValueException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.utils.Constants;
import com.utils.PwdEncoder;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

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
        private String profileImage;

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
            this.profileImage = builder.profileImage;
        }

        public static class Builder {
            private final String name;
            private final String password;
            private String email;
            private final String address;
            private String addressDetail = "";
            private SocialType socialType = SocialType.NORMAL;
            private long socialId;
            private String profileImage;

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

            public Builder profileImage(String value) {
                this.profileImage = value;
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

        public String getProfileImage() {
            return profileImage;
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
        private String resignedAt;
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
            session.setAttribute(Constants.CURRENT_MEMBER_SESSION_NAME, this);
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

        public String getResignedAt() {
            return resignedAt;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Update {
        private long id;
        private String name;
        private String address;
        private String addressDetail;
        private String profileImage;
        private String introduction;
        private SocialType socialType;
        private long socialId;
        private MemberRole authority;

        public Update() {
            //
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getAddressDetail() {
            return addressDetail;
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

        public MemberRole getAuthority() {
            return authority;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }
    }

    public static class SignIn {
        private String email;
        private String password;

        public SignIn() {
            //
        }

        public SignIn(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public void encPassword() {
            try {
                this.password = PwdEncoder.encrypt(this.password);
            } catch (NoSuchAlgorithmException e) {
                throw new InvalidValueException("알 수 없는 이유로 로그인 인증 실패");
            }
        }
    }

    public static class Delete {
        private long id;
        private String password;
        private long socialId;
        private String profileImage;

        public Delete() {
            //
        }

        public Delete(long id, String password, long socialId) {
            this.id = id;
            this.password = encPassword(password);
            this.socialId = socialId;
            this.profileImage = Constants.RESIGN_MEM_PROFILE;
        }

        public long getId() {
            return id;
        }

        public String getPassword() {
            return password;
        }

        public long getSocialId() {
            return socialId;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String encPassword(String password) {
            try {
                return PwdEncoder.encrypt(password);
            } catch (NoSuchAlgorithmException e) {
                throw new InvalidValueException("알 수 없는 이유로 회원 탈퇴 인증 실패");
            }
        }
    }
}
