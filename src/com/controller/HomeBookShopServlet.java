package com.controller;


import com.dto.bookshop.BookShopVO;
import com.dto.common.PageDTO;
import com.service.bookshop.BookShopService;
import com.service.bookshop.BookShopServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookshoplist")
public class HomeBookShopServlet extends HttpServlet {
    private static final BookShopService bookShopService = new BookShopServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String currentPageParam = request.getParameter("page");
        int currentPage = (currentPageParam == null) ? 1 : Integer.parseInt(currentPageParam);

        PageDTO<BookShopVO.Book> bookShopDTOPageDTO = bookShopService.findAllPageable(4, currentPage);

        request.setAttribute("page", currentPage);
        request.setAttribute("bookshoplist", bookShopDTOPageDTO.gettList());

        handlePagination(request, currentPage, 5);
        request.getRequestDispatcher("pages/main/bookshoplist.jsp").forward(request, response);
    }

    private void handlePagination(HttpServletRequest request, int currentPage, int totalPage) {
        boolean hasNext = true;
        boolean hasPrev = true;
        if (currentPage == 1) {
            hasPrev = false;
        }
        if (currentPage == totalPage) {
            hasNext = false;
        }
        request.setAttribute("prev", hasPrev);
        request.setAttribute("next", hasNext);
    }
}
