package com.controller.book;


import com.controller.common.JSONResponse;
import com.dto.book.BookApiDTO;
import com.dto.book.BookDTO;
import com.dto.bookshop.BookQueryType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.book.BookAPIService;
import com.service.book.BookService;
import com.service.book.BookServiceImpl;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/books")
public class BookServlet extends HttpServlet {
    /**
     * Book API 로 부터 책을 조회한다.
     * GET
     * /books
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String isbn = request.getParameter("isbn");
        BookAPIService service = new BookAPIService();
        JSONObject object = service.searchBook(BookQueryType.ISBN, isbn);
        BookApiDTO apiDTO = new ObjectMapper().readValue(object.toString(), BookApiDTO.class);
        JSONResponse.send(response, apiDTO, 200);
    }

}
