package com.service.bookshop;

import com.config.MySqlSessionFactory;
import com.dao.bookshop.BookShopDAO;
import com.dto.bookshop.BookShopDTO;
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
}
