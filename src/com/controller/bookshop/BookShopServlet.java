package com.controller.bookshop;


import com.controller.common.JSONResponse;
import com.dto.book.BookApiDTO;
import com.dto.book.BookDTO;
import com.dto.bookshop.BookShopDTO;
import com.dto.member.MemberDTO;
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

@WebServlet("/bookshop")
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
        bookShopDTO.setBook(new BookDTO(bookApiDTO));
        bookShopDTO.setSellerId((MemberDTO.Info) request.getSession().getAttribute("meminfo"));

        int status = bookShopService.submit(bookShopDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("result",status);
        JSONResponse.send(response,map,200);
    }
}
