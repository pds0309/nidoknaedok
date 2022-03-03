package com.controller.common;

import com.errors.exception.UserAccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/session")
public class CompareSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getHeader("referer")==null) {
            throw new UserAccessDeniedException("잘못된 접근 식별됨");
        }

        HttpSession session = request.getSession();
        String result = session.getAttribute("meminfo") == null ? "NO" : "YES";

        Map<String, String> map = new HashMap<>();
        map.put("result", result);
        JSONResponse.send(response, map, 200);
    }
}
