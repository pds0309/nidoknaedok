package com.service.bookshop;

import com.config.MySqlSessionFactory;
import com.dao.bookshop.BookShopDAO;
import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.common.PageDTO;
import org.apache.ibatis.session.SqlSession;

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
        PageDTO pageDTO = null;
        try {
            pageDTO = bookShopDAO.findAllWithPagination(session, currentPage, recordAmountPerPage);
        } finally {
            session.close();
        }
        return pageDTO;
    }
}
