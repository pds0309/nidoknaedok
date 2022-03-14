package com.controller.bookshop;


import com.controller.common.JSONResponse;
import com.dto.member.MemberDTO;
import com.service.bookshop.BookShopService;
import com.service.bookshop.BookShopServiceImpl;
import com.utils.Constants;
import com.utils.SessionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookshops/stats")
public class BookShopStatisticServlet extends HttpServlet {
    private static final BookShopService bookShopService = new BookShopServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long memberId =
                ((MemberDTO.Info) SessionHandler.verify(request.getSession(), Constants.CURRENT_MEMBER_SESSION_NAME)).getId();
        JSONResponse.send(response, bookShopService.findBookShopStatsByMemberId(memberId), response.getStatus());
    }
}
