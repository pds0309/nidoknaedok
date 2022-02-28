package com.controller.member;

import com.controller.common.Cookies;
import com.dto.member.MemberDTO;
import com.dto.member.SocialType;
import com.errors.exception.InvalidValueException;
import com.errors.exception.UserAccessDeniedException;
import com.service.member.MemberService;
import com.service.member.MemberServiceImpl;
import com.utils.Constants;
import com.utils.PwdEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@WebServlet("/members")
public class MemberServlet extends HttpServlet {

    private final MemberService memberService = new MemberServiceImpl();

    /**
     * /members
     * POST
     * 회원을 등록하다
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validReferer(request);

        Optional<Cookie> optionalCookie = Cookies.get(request, Constants.AUTH_COOKIE_NAME);
        boolean oAuthType = false;
        if (optionalCookie.isPresent()) {
            oAuthType = checkOAuthJoin(request, optionalCookie.get());
        }

        String name = "";
        String password = "";
        String email = "";
        String address = "";
        String addressDetail = "";
        String signType = "";
        try {
            name = request.getParameter("name").trim();
            password = request.getParameter("password").trim();
            email = request.getParameter("email").trim();
            address = request.getParameter("address").trim();
            addressDetail = request.getParameter("addressdetail").trim();
            signType = request.getParameter("signType");
            password = PwdEncoder.encrypt(password);
        } catch (NullPointerException e) {
            throw new InvalidValueException("회원등록 입력 정보 누락 식별");
        } catch (NoSuchAlgorithmException e) {
            InvalidValueException ex = new InvalidValueException("일부 입력 데이터 처리에 실패해 회원등록 실패");
            e.initCause(ex);
            throw ex;
        }

        MemberDTO.SignUp.Builder memberDTO =
                new MemberDTO.SignUp.Builder(name, password, address)
                        .email(email)
                        .addressDetail(addressDetail);

        if (oAuthType) {
            memberDTO.socialType(SocialType.valueOf(signType))
                    .socialId(Long.parseLong(request.getParameter("kid")));
        }

        int status = memberService.signUpMember(memberDTO.build());
        if (oAuthType && status == 1) {
            memberService.findByEmail(email).get().addSession(request.getSession());
        }
        response.sendRedirect("home");
    }

    /**
     * 일반 멤버 가입인지 OAuth 멤버 가입인지 검증
     *
     * @return: true: OAuth 형태로 멤버 가입
     */
    private boolean checkOAuthJoin(HttpServletRequest request, Cookie cookie) {
        String paramToken = request.getParameter("oauth2");

        if (paramToken == null || "".equals(paramToken)) {
            return false;
        }
        if (!cookie.getValue().equals(paramToken)) {
            throw new UserAccessDeniedException("가입 도중 잘못된 접근 식별됨");
        }
        return true;
    }

    /**
     * 멤버등록을 요청한 위치는 반드시 로그인 경로여야함을 검증한다
     */
    private void validReferer(HttpServletRequest request) {
        String referer = request.getHeader("referer");

        if (referer == null || !referer.contains("join")) {
            throw new UserAccessDeniedException("잘못된 접근 식별됨");
        }
    }
}
