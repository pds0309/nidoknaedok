package com.controller.member.oauth;

import com.config.openapi.ApiKey;
import com.controller.common.Cookies;
import com.controller.common.JSONResponse;
import com.dto.member.MemberDTO;
import com.errors.ErrorCode;
import com.errors.ErrorResponse;
import com.errors.exception.UserAccessDeniedException;
import com.service.member.MemberService;
import com.service.member.MemberServiceImpl;
import com.service.oauth.KakaoOAuth2;
import com.service.oauth.SocialOAuth2;
import com.utils.Constants;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/members/oauth/kakao")
public class KakaoLoginServlet extends HttpServlet {
    private static final SocialOAuth2 oauth2 = new KakaoOAuth2();
    private static final MemberService memberService = new MemberServiceImpl();
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * /members/kakao
     * GET
     * 카카오 OAuth 회원을 등록 요청을 한다.
     * 이메일 수집 동의를 하지 않으면 등록하지 못한다.
     * code를 얻어와 토큰을 얻어오고 토큰을 통해 사용자 정보를 얻는다.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        JSONObject tokenObject = null;
        try {
            tokenObject = new JSONObject(oauth2.getAccessToken(code, request.getRequestURL()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserAccessDeniedException("잘못된 접근입니다");
        }
        // 이메일 수집 동의 여부를 체크한다.
        if (userNotCheckedEmailScope(tokenObject.getString("scope"))) {
            emailConsent(request, response);
            return;
        }
        // 토큰으로부터 사용자 정보를 얻는다.
        String memberInfo = oauth2.getMemberInfoByToken(tokenObject.getString("access_token"));
        JSONObject memberObject = new JSONObject(memberInfo);
        // 멤버 정보를 보고 처음 온 사람이면 회원가입 페이지로 보내 추가 정보 입력을 받는다.
        MemberDTO.Info info =
                memberService.findByEmail(memberObject.getJSONObject("kakao_account").getString("email")).orElse(null);
        // kakao access_token 을 쿠키에 저장한다.
        Cookies.add(Constants.AUTH_COOKIE_NAME, tokenObject.getString("access_token"),
                Constants.AUTH_COOKIE_TIMEOUT, response);
        // 신규 등록 회원이면 부가 정보를 위해 가입 페이지로 이동시킨다.
        if (info == null) {
            response.sendRedirect(request.getContextPath() + "/kakaojoin");
            return;
        }
        // 이메일 검사
        // 기존 카카오 OAuth 멤버면 로그인을 시킨다.
        if ("kakao".equals(info.getSocialType().getName()) && memberObject.getLong("id") == info.getSocialId()) {
            info.addSession(request.getSession());
            Cookie cookie = Cookies.get(request, "req").orElse(null);
            response.sendRedirect(request.getContextPath() + "/" + (cookie == null ? "home" : cookie.getValue()));
            return;
        }
        // 이메일이 있는 사용자인데 이전에 탈퇴한 사용자라면 인증을 금지시킨다.
        try {
            memberService.validMemberResigned(info);
        } catch (Exception e) {
            logger.error(e.getMessage());
            request.setAttribute("errorinfo", ErrorResponse.of(ErrorCode.ACCESS_DENIED,
                    e.getMessage()));
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
            return;
        }
        // 기존 회원인데 카카오 OAuth 멤버가 아닌 경우
        // 연동을 묻는 화면으로 리다이렉트 시킨다.
        request.getSession().setAttribute("memid", info.getId());
        response.sendRedirect(request.getContextPath() + "/kakaointeg");
    }

    /**
     * 이메일이 필수인 서비스라 동의를 다시 구해야 한다.
     */
    private void emailConsent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("client_id", ApiKey.getInstance().getKakaoOAuthId());
        paramMap.put("redirect_uri", request.getRequestURL());
        paramMap.put("response_type", "code");
        paramMap.put("scope", "account_email");
        String postData = oauth2.appendParamForRequest(paramMap);

        response.sendRedirect(SocialOAuth2.KAKAO_ADDINFO + postData);
    }

    /**
     * 이메일 수집 동의를 했나 검사한다.
     *
     * @param scope: 동의 정보 목록이다.
     * @return: 동의안했으면 true
     */
    private boolean userNotCheckedEmailScope(String scope) {
        return !scope.contains("account_email");
    }
}
