package com.controller.bookshop;

import com.controller.common.JSONResponse;
import com.dto.book.BookApiDTO;
import com.dto.book.BookDTO;
import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopHistoryDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.common.PageDTO;
import com.dto.member.MemberDTO;
import com.errors.exception.UserAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.book.BookService;
import com.service.book.BookServiceImpl;
import com.service.bookshop.BookShopService;
import com.service.bookshop.BookShopServiceImpl;
import com.utils.Constants;
import com.utils.SessionHandler;

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
        bookShopDTO.setSellerId(((MemberDTO.Info) (request.getSession().getAttribute(Constants.CURRENT_MEMBER_SESSION_NAME))).getId());

        int status = bookShopService.submit(bookShopDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("result", status);
        JSONResponse.send(response, map, 200);
    }

    /**
     * 등록된 거래를 조회한다.
     * GET
     * /bookshop?param
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookshopId = request.getParameter("bookshopid");
        if (validParam(bookshopId)) {
            BookShopVO.Member bookShop = bookShopService.findByBookshopId(Long.parseLong(bookshopId));
            setItIsMe(request, bookShop, bookShop.getMember().getId());
            JSONResponse.send(response, bookShop, response.getStatus());
            return;
        }

        MemberDTO.Info memberInfo =
                SessionHandler.verify(request.getSession(), Constants.CURRENT_MEMBER_SESSION_NAME);
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("sellerId", memberInfo.getId());

        int currentPage = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));
        PageDTO<BookShopVO.Book> bookShopPageDTO = bookShopService.findAllByMemberId(paramMap, 5, currentPage);
        int totalPageNum = (int) Math.ceil(Double.parseDouble(request.getParameter("total")) / bookShopPageDTO.getPerPage());

        Map<String, Object> map = new HashMap<>();
        map.put("bookshops", bookShopPageDTO.gettList());
        map.put("prev", currentPage != 1);
        map.put("next", currentPage != totalPageNum);
        map.put("page", currentPage);
        JSONResponse.send(response, map, response.getStatus());
    }

    /**
     * 등록된 거래를 수정한다.
     * PUT
     * /bookshop
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberDTO.Info sessionMember = SessionHandler.verify(request.getSession(), Constants.CURRENT_MEMBER_SESSION_NAME);
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        bufferedReader.close();

        int status =
                bookShopService.updateBookShopInfo(new ObjectMapper().readValue(read, BookShopDTO.class), sessionMember.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("result", status);
        JSONResponse.send(response, map, 200);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader bufferedReader = request.getReader();
        String read = bufferedReader.readLine();
        bufferedReader.close();
        BookShopDTO bookShopDTO = objectMapper.readValue(read, BookShopDTO.class);

        if (!SessionHandler.isItMe(request.getSession(), bookShopDTO.getSellerId())) {
            throw new UserAccessDeniedException("등록된 거래 취소에 대한 권한이 없습니다");
        }

        bookShopService.deleteByBookshopId(bookShopDTO.getBookshopId());
        JSONResponse.send(response, null, 201);
    }

    private void setItIsMe(HttpServletRequest request, BookShopVO.Member bookShop, long id) {
        if (SessionHandler.isItMe(request.getSession(), id)) {
            bookShop.setItIsMe(true);
        }
    }

    private boolean validParam(String param) {
        return param != null && !"".equals(param);
    }
}
