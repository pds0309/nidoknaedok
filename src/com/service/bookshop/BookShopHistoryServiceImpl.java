package com.service.bookshop;

import com.config.MySqlSessionFactory;
import com.dao.bookshop.BookShopDAO;
import com.dao.bookshop.BookShopHistoryDAO;
import com.dto.bookshop.BookShopHistoryDTO;
import com.dto.bookshop.BookShopHistoryVO;
import com.errors.exception.InvalidValueException;
import com.errors.exception.NotAcceptableValueException;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class BookShopHistoryServiceImpl implements BookShopHistoryService {
    private static final BookShopDAO bookShopDAO = new BookShopDAO();
    private static final BookShopHistoryDAO historyDAO = new BookShopHistoryDAO();

    @Override
    public int submit(BookShopHistoryDTO bookShopHistoryDTO) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = historyDAO.submit(session, bookShopHistoryDTO);
            if (bookShopDAO.updateSellStatus(session, bookShopHistoryDTO) == 0) {
                throw new NotAcceptableValueException("탈퇴한 회원과 거래하실 수 없습니다");
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotAcceptableValueException("이미 신청하셨거나 완료된 거래입니다");
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
                    .orElse(null);
        } finally {
            session.close();
        }
    }

    @Override
    public int deleteByBookshopIdId(Map<String, Long> paramMap) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = historyDAO.deleteByBookshopIdId(session, paramMap);
            if (status == 0) {
                throw new InvalidValueException("거래 취소에 실패했습니다");
            }
            session.commit();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public List<BookShopHistoryVO.Member> findByBookshopId(long bookshopId) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return historyDAO.findByBookshopId(session, bookshopId);
        } finally {
            session.close();
        }
    }

    @Override
    public int updateHistoryById(BookShopHistoryDTO historyDTO) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = historyDAO.updateHistoryById(session, historyDTO);
            bookShopDAO.updateSellStatus(session, historyDTO);
            if (status == 0) {
                throw new NotAcceptableValueException("이미 다른분과 약속하지 않으셨나요?");
            }
            session.commit();
        } finally {
            session.close();
        }
        return status;
    }
}
