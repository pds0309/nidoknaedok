package com.service.bookshop;

import com.config.MySqlSessionFactory;
import com.dao.bookshop.BookShopDAO;
import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.common.PageDTO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class BookShopServiceImpl implements BookShopService {
    private static final BookShopDAO bookShopDAO = new BookShopDAO();

    public int submit(BookShopDTO bookShopDTO) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = bookShopDAO.submit(session, bookShopDTO);
            session.commit();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public PageDTO<BookShopVO.Book> findAllPageable(int recordAmountPerPage, int currentPage) {
        SqlSession session = MySqlSessionFactory.getSession();
        PageDTO<BookShopVO.Book> pageDTO = null;
        try {
            pageDTO = bookShopDAO.findAllWithPagination(session, currentPage, recordAmountPerPage);
        } finally {
            session.close();
        }
        return pageDTO;
    }

    @Override
    public List<BookShopVO.Member> findAllByParams(Map<String, Object> paramMap, int recordAmountPerPage, int startPage) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return bookShopDAO.findAllByParams(session, startPage, recordAmountPerPage, paramMap);
        } finally {
            session.close();
        }
    }
}
