package com.controller.ui;

import com.controller.common.Cookies;
import com.dto.member.MemberDTO;
import com.dto.member.SocialType;
import com.dto.oauth.KakaoUserDTO;
import com.errors.exception.UserAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.member.MemberService;
import com.service.member.MemberServiceImpl;
import com.service.oauth.KakaoOAuth2;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/kakaointeg")
public class KakaoIntegrationUIServlet extends HttpServlet {
    private final MemberService service = new MemberServiceImpl();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie accessTokenCookie = Cookies.get(request, "_katkauta")
                .orElseThrow(() -> new UserAccessDeniedException("인증 유효 정보가 만료되었거나 접근 권한이 없습니다."));

        Object id = request.getSession().getAttribute("memid");
        if (id == null) {
            throw new UserAccessDeniedException("인증 유효 정보가 만료되었거나 접근 권한이 없습니다.");
        }

        String kakaoInfo = new KakaoOAuth2().getMemberInfoByToken(accessTokenCookie.getValue());
        KakaoUserDTO kakaoUserDTO = mapper.readValue(kakaoInfo, KakaoUserDTO.class);

        JSONObject updateList = new JSONObject();
        updateList.put("id", (long) id);
        updateList.put("social_id", kakaoUserDTO.getId());
        updateList.put("social_type", SocialType.KAKAO.getName().toUpperCase());
        updateList.put("profile_image", kakaoUserDTO.getProperties().getThumbnailImage());

        request.setAttribute("integration", mapper.readValue(updateList.toString(),MemberDTO.Update.class));
        service.findById((long)id).get().addSession(request.getSession());
        request.getRequestDispatcher("pages/OAuthIntegration.jsp").forward(request, response);
    }
}
