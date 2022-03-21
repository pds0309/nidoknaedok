package com.controller.bookshop;

import com.controller.common.JSONResponse;
import com.dto.bookshop.BookShopHistoryDTO;
import com.errors.exception.UserAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.bookshop.BookShopHistoryService;
import com.service.bookshop.BookShopHistoryServiceImpl;
import com.utils.SessionHandler;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        bufferedReader.close();
        JSONObject result = new JSONObject(read);

        if (!SessionHandler.isItMe(request.getSession(), result.getLong("seller_id"))) {
            throw new UserAccessDeniedException("잘못된 접근입니다.");
        }
        historyService.updateHistoryById(new ObjectMapper().readValue(read, BookShopHistoryDTO.class));
        JSONResponse.send(response, null, response.getStatus());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        bufferedReader.close();
        JSONObject result = new JSONObject(read);

        long memberId = result.getLong("member_id");

        if (!SessionHandler.isItMe(request.getSession(), memberId)) {
            throw new UserAccessDeniedException("접근 권한이 없습니다");
        }
        long bookshopId = result.getLong("bookshop_id");
        historyService.deleteByBookshopId(bookshopId);
        JSONResponse.send(response, null, 201);
    }
}
