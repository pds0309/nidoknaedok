package com.controller.bookshop;

import com.controller.common.JSONResponse;
import com.dto.bookshop.BookShopHistoryDTO;
import com.dto.bookshop.BookShopStatusCode;
import com.dto.member.MemberDTO;
import com.errors.exception.InvalidValueException;
import com.errors.exception.UserAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.bookshop.BookShopHistoryService;
import com.service.bookshop.BookShopHistoryServiceImpl;
import com.utils.Constants;
import com.utils.SessionHandler;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bookshops/history")
public class BookShopHistoryServlet extends HttpServlet {
    private static final BookShopHistoryService historyService = new BookShopHistoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long memberId = ((MemberDTO.Info) SessionHandler.verify(request.getSession(),
                Constants.CURRENT_MEMBER_SESSION_NAME)).getId();
        long bookshopId = Long.parseLong(request.getParameter("bookshopid"));

        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("memberId", memberId);
        paramMap.put("bookshopId", bookshopId);

        BookShopHistoryDTO historyDTO = historyService.findOneByBookshopIdId(paramMap);
        JSONResponse.send(response, historyDTO, historyDTO == null ? 204 : 200);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long memberId = ((MemberDTO.Info) SessionHandler.verify(request.getSession(),
                Constants.CURRENT_MEMBER_SESSION_NAME)).getId();

        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        bufferedReader.close();
        JSONObject resultObject = new JSONObject(read);

        BookShopHistoryDTO.Builder historyDTO = new BookShopHistoryDTO.Builder();
        try {
            historyDTO.bookshopId(resultObject.getLong("bookshop_id"))
                    .memberId(memberId)
                    .memo(resultObject.getString("memo"))
                    .statusId(BookShopStatusCode.SUBMIT);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidValueException("요청 정보가 올바르지 않습니다");
        }

        int status = historyService.submit(historyDTO.build());
        Map<String, Object> map = new HashMap<>();
        map.put("result", status);
        JSONResponse.send(response, map, 201);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        bufferedReader.close();
        JSONObject resultObject = new JSONObject(read);

        Map<String, Long> paramMap = new HashMap<>();
        try {
            paramMap.put("memberId", resultObject.getLong("member_id"));
            paramMap.put("bookshopId", resultObject.getLong("bookshop_id"));
        } catch (Exception e) {
            throw new InvalidValueException("요청 정보가 올바르지 않습니다");
        }
        if (!SessionHandler.isItMe(request.getSession(), paramMap.get("memberId"))) {
            throw new UserAccessDeniedException("잘못된 접근입니다");
        }
        historyService.deleteByBookshopIdId(paramMap);

        Map<String, Object> map = new HashMap<>();
        JSONResponse.send(response, map, 201);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        bufferedReader.close();
        JSONObject result = new JSONObject(read);

        if (SessionHandler.isItMe(request.getSession(), result.getLong("seller_id"))) {
            throw new UserAccessDeniedException("잘못된 접근입니다.");
        }

        historyService.updateHistoryById(new ObjectMapper().readValue(read, BookShopHistoryDTO.class));
        JSONResponse.send(response, null, response.getStatus());
    }
}
