package com.service.bookshop;

import com.config.MySqlSessionFactory;
import com.dao.bookshop.BookShopDAO;
import com.dao.bookshop.BookShopHistoryDAO;
import com.dto.bookshop.BookShopHistoryDTO;
import com.errors.exception.NotAcceptableValueException;
import org.apache.ibatis.session.SqlSession;

public class BookShopHistoryServiceImpl implements BookShopHistoryService {
    private static final BookShopDAO bookShopDAO = new BookShopDAO();
    private static final BookShopHistoryDAO historyDAO = new BookShopHistoryDAO();

    @Override
    public int submit(BookShopHistoryDTO bookShopHistoryDTO) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            if (bookShopDAO.updateSellStatus(session, bookShopHistoryDTO.getBookshopId()) == 1) {
                status = historyDAO.submit(session, bookShopHistoryDTO);
                session.commit();
            } else {
                throw new NotAcceptableValueException("이미 완료된 거래입니다");
            }
        } finally {
            session.close();
        }
        return status;
    }
}
