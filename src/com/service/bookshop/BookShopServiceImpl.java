package com.service.bookshop;

import com.config.MySqlSessionFactory;
import com.dao.bookshop.BookShopDAO;
import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.common.PageDTO;
import com.errors.exception.UserAccessDeniedException;
import com.errors.exception.UserNotFoundException;
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

    @Override
    public BookShopVO.Member findByBookshopId(long bookshopId) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return bookShopDAO.findByBookshopId(session, bookshopId)
                    .orElseThrow(() -> new UserNotFoundException("요청에 대한 거래 내역을 찾을 수 없음"));
        } finally {
            session.close();
        }
    }

    @Override
    public int updateBookShopInfo(BookShopDTO bookShopDTO, long sessionMemberId) {
        validMemberId(sessionMemberId, bookShopDTO.getSellerId());
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = bookShopDAO.updateBookShopInfo(session, bookShopDTO);
            session.commit();
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public PageDTO<BookShopVO.Book> findAllByMemberId(Map<String, Long> paramMap, int recordAmountPerPage, int currentPage) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return bookShopDAO.findAllByMemberId(session, paramMap, currentPage, recordAmountPerPage);
        } finally {
            session.close();
        }
    }

    @Override
    public Map<String, Integer> findBookShopStatsByMemberId(long memberId) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return bookShopDAO.findBookShopStatsByMemberId(session, memberId);
        }
        finally {
            session.close();
        }
    }

    private void validMemberId(long sessionMemberId, long currentMemberId) {
        if (sessionMemberId != currentMemberId) {
            throw new UserAccessDeniedException("해당 작업에 대해 권한이 없습니다.");
        }
    }
}
