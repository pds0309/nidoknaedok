package com.controller.bookshop;


import com.controller.common.JSONResponse;
import com.dto.book.BookApiDTO;
import com.dto.book.BookDTO;
import com.dto.bookshop.BookShopDTO;
import com.dto.member.MemberDTO;
import com.errors.exception.InvalidValueException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.book.BookService;
import com.service.book.BookServiceImpl;
import com.service.bookshop.BookShopService;
import com.service.bookshop.BookShopServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bookshops")
public class BookShopServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final BookService bookService = new BookServiceImpl();
    private static final BookShopService bookShopService = new BookShopServiceImpl();

    /**
     * 책 등록 커뮤니티에 책과 함께 거래를 등록한다.
     * POST
     * /bookshop
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        bufferedReader.close();
        BookApiDTO bookApiDTO = objectMapper.readValue(read, BookApiDTO.class);
        BookDTO bookDTO = bookService.findById(Long.parseLong(bookApiDTO.getIsbn()));
        if (bookDTO == null) {
            bookDTO = new BookDTO(bookApiDTO);
            bookService.submit(bookDTO);
        }
        BookShopDTO bookShopDTO = objectMapper.readValue(read, BookShopDTO.class);
        bookShopDTO.setBookId(new BookDTO(bookApiDTO).getBookId());
        bookShopDTO.setSellerId(((MemberDTO.Info) (request.getSession().getAttribute("meminfo"))).getId());

        int status = bookShopService.submit(bookShopDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("result", status);
        JSONResponse.send(response, map, 200);
    }

    /**
     * 등록된 거래를 조회한다.
     * GET
     * /bookshop
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookshopId = request.getParameter("bookshopid");
        if (validParam(bookshopId)) {
            JSONResponse.send(response, bookShopService.findByBookshopId(Long.parseLong(bookshopId)), response.getStatus());
            return;
        }
        throw new InvalidValueException("거래 조회를 위한 인자가 부적절함");
    }

    private boolean validParam(String param) {
        return param != null && !"".equals(param);
    }
}
