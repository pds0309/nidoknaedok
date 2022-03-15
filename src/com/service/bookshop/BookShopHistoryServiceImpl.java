package com.service.bookshop;

import com.config.MySqlSessionFactory;
import com.dao.bookshop.BookShopDAO;
import com.dao.bookshop.BookShopHistoryDAO;
import com.dto.bookshop.BookShopHistoryDTO;
import com.errors.exception.NotAcceptableValueException;
import com.errors.exception.UserNotFoundException;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

public class BookShopHistoryServiceImpl implements BookShopHistoryService {
    private static final BookShopDAO bookShopDAO = new BookShopDAO();
    private static final BookShopHistoryDAO historyDAO = new BookShopHistoryDAO();

    @Override
    public int submit(BookShopHistoryDTO bookShopHistoryDTO) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            if (bookShopDAO.updateSellStatus(session, bookShopHistoryDTO.getBookshopId()) == 1) {
                try {
                    status = historyDAO.submit(session, bookShopHistoryDTO);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new NotAcceptableValueException("이미 신청하셨습니다");
                }
                session.commit();
            } else {
                throw new NotAcceptableValueException("이미 완료된 거래거나 탈퇴한 회원의 거래입니다");
            }
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public BookShopHistoryDTO findOneByBookshopIdId(Map<String, Long> paramMap) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return historyDAO.findOneByBookshopIdId(session, paramMap)
                    .orElseThrow(() -> new UserNotFoundException("요청 데이터를 찾을 수 없음"));
        } finally {
            session.close();
        }
    }
}
