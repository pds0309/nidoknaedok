package com.controller.bookshop;

import com.controller.common.JSONResponse;
import com.service.bookshop.BookShopHistoryService;
import com.service.bookshop.BookShopHistoryServiceImpl;
import com.utils.SessionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bookshops/historylist")
public class BookShopHistoryListServlet extends HttpServlet {
    private static final BookShopHistoryService historyService = new BookShopHistoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long memberId = request.getParameter("memberid") == null ? 0 : Long.parseLong(request.getParameter("memberid"));

        if (!SessionHandler.isItMe(request.getSession(), memberId)) {
            throw new AccessDeniedException("조회 권한 없음");
        }
        long bookshopId = request.getParameter("bookshopid") == null ? 0 : Long.parseLong(request.getParameter("bookshopid"));
        JSONResponse.send(response, historyService.findByBookshopId(bookshopId), response.getStatus());
    }
}
