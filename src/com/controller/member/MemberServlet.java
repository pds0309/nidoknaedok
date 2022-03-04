package com.controller.member;

import com.controller.common.Cookies;
import com.controller.common.JSONResponse;
import com.dto.member.MemberDTO;
import com.dto.member.SocialType;
import com.errors.exception.InvalidValueException;
import com.errors.exception.UserAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.member.MemberService;
import com.service.member.MemberServiceImpl;
import com.utils.Constants;
import com.utils.PwdEncoder;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        validReferer(request, "join");

        Optional<Cookie> optionalCookie = Cookies.get(request, Constants.AUTH_COOKIE_NAME);
        boolean oAuthType = false;
        if (optionalCookie.isPresent()) {
            oAuthType = checkOAuthJoin(request, optionalCookie.get());
        }
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        if (read == null || "".equals(read)) {
            throw new InvalidValueException("회원등록 입력 정보 누락 식별");
        }
        bufferedReader.close();

        JSONObject readResult = new JSONObject(read);
        String name = "";
        String password = "";
        String email = "";
        String address = "";
        String addressDetail = "";
        String signType = "";

        try {
            name = readResult.getString("name").trim();
            password = PwdEncoder.encrypt(readResult.getString("password").trim());
            email = readResult.getString("email").trim();
            address = readResult.getString("address").trim();
            addressDetail = readResult.getString("addressdetail").trim();
            if (addressDetail.length() >= 60) {
                addressDetail = addressDetail.substring(0, 60);
            }
            signType = readResult.getString("signType");
        } catch (Exception e) {
            throw new InvalidValueException("회원등록 입력 정보 누락 식별");
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
            request.getSession().invalidate();
            HttpSession session = request.getSession();
            memberService.findByEmail(email).get().addSession(session);
        }

        Map<String, String> map = new HashMap<>();
        map.put("result", String.valueOf(status));
        map.put("message", "회원 가입 성공!");
        JSONResponse.send(response, map, 201);
    }

    /**
     * 회원을 수정하다
     * 1.OAuth -> 일반 전환
     * 2.일반 -> OAuth 전환
     * 3.회원 수정
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validReferer(request, "");

        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        if (read == null || "".equals(read)) {
            throw new InvalidValueException("회원 정보 누락 식별됨");
        }
        bufferedReader.close();

        MemberDTO.Update updateDTO = new ObjectMapper().readValue(read, MemberDTO.Update.class);

        int status = memberService.updateMember(updateDTO);
        Map<String, String> map = new HashMap<>();
        map.put("result", String.valueOf(status));
        map.put("message", "회원 수정 성공!");
        request.getSession().invalidate();
        HttpSession session = request.getSession();
        memberService.findById(updateDTO.getId()).get().addSession(session);
        JSONResponse.send(response, map, 201);
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
    private void validReferer(HttpServletRequest request, String refererName) {
        String referer = request.getHeader("referer");

        if (referer == null || !referer.contains(refererName)) {
            throw new UserAccessDeniedException("잘못된 접근 식별됨");
        }
    }
}
