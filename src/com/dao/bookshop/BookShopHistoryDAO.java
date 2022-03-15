package com.dao.bookshop;

import com.dto.bookshop.BookShopHistoryDTO;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;
import java.util.Optional;

public class BookShopHistoryDAO {
    private static final String DIR = "com.config.BookShopHistoryMapper.";

    public int submit(SqlSession session, BookShopHistoryDTO bookShopHistoryDTO) {
        return session.insert(DIR + "submit", bookShopHistoryDTO);
    }

    public Optional<BookShopHistoryDTO> findOneByBookshopIdId(SqlSession session, Map<String,Long> paramMap){
        return Optional.ofNullable(session.selectOne(DIR + "findOneByBookshopIdId", paramMap));
    }
}
