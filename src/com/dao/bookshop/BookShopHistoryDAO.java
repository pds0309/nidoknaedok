package com.dao.bookshop;

import com.dto.bookshop.BookShopHistoryDTO;
import com.dto.bookshop.BookShopHistoryVO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookShopHistoryDAO {
    private static final String DIR = "com.config.BookShopHistoryMapper.";

    public int submit(SqlSession session, BookShopHistoryDTO bookShopHistoryDTO) {
        return session.insert(DIR + "submit", bookShopHistoryDTO);
    }

    public Optional<BookShopHistoryDTO> findOneByBookshopIdId(SqlSession session, Map<String, Long> paramMap) {
        return Optional.ofNullable(session.selectOne(DIR + "findOneByBookshopIdId", paramMap));
    }

    public int deleteByBookshopIdId(SqlSession session, Map<String, Long> paramMap) {
        return session.delete(DIR + "deleteByBookshopIdId", paramMap);
    }

    public int deleteByBookshopId(SqlSession session, long bookshopId) {
        return session.delete(DIR + "deleteByBookshopId", bookshopId);
    }

    public List<BookShopHistoryVO.Member> findByBookshopId(SqlSession session, long bookshopId) {
        return session.selectList(DIR + "findByBookshopId", bookshopId);
    }

    public int updateHistoryById(SqlSession session, BookShopHistoryDTO historyDTO) {
        return session.update(DIR + "updateHistoryById", historyDTO);
    }
}
