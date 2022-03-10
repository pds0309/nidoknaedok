package com.controller;


import com.dto.bookshop.BookShopStatusCode;
import com.dto.bookshop.BookShopVO;
import com.dto.bookshop.CategoryCode;
import com.dto.bookshop.TradeCode;
import com.errors.exception.InvalidValueException;
import com.service.bookshop.BookShopService;
import com.service.bookshop.BookShopServiceImpl;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/bookshoptype")
public class HomeBookShopTypeServlet extends HttpServlet {
    private static final BookShopService bookShopService = new BookShopServiceImpl();
    private static final int AMOUNT_OF_PAGE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, Object> paramMap = new HashMap<>();
        String tradeCode = request.getParameter("tradecode");
        String categoryCode = request.getParameter("categorycode");
        String statusCode = request.getParameter("statuscode");

        validTradeType(tradeCode);

        paramMap.put("tradeCode", TradeCode.valueOf(tradeCode));
        paramMap.put("categoryCode", validEnumParam(categoryCode) ? CategoryCode.valueOf(categoryCode) : null);
        paramMap.put("statusCode", validEnumParam(statusCode) ? BookShopStatusCode.valueOf(statusCode) : null);

        paramMap.put("id", request.getParameter("id"));

        String startPageParam = request.getParameter("page");
        int startPage = (startPageParam == null) ? 0 : Integer.parseInt(startPageParam);

        List<BookShopVO.Member> bookshoplist = bookShopService.findAllByParams(paramMap, AMOUNT_OF_PAGE, startPage);

        request.setAttribute("id", TradeCode.valueOf(tradeCode).getSelltypeId());
        request.setAttribute("start", startPage);
        request.setAttribute("amount", AMOUNT_OF_PAGE);
        request.setAttribute("bookshoplist", bookshoplist);
        request.getRequestDispatcher("pages/main/bookshoptype.jsp").forward(request, response);
    }

    private void validTradeType(String name) {
        if (name == null || "".equals(name)) {
            throw new InvalidValueException("요청 타입이 불분명함");
        }
    }

    private boolean validEnumParam(String name) {
        return name != null && !"".equals(name);
    }
}
