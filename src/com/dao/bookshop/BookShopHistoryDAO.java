package com.dao.bookshop;

import com.dto.bookshop.BookShopHistoryDTO;
import org.apache.ibatis.session.SqlSession;

public class BookShopHistoryDAO {
    private static final String DIR = "com.config.BookShopHistoryMapper.";

    public int submit(SqlSession session, BookShopHistoryDTO bookShopHistoryDTO) {
        return session.insert(DIR + "submit", bookShopHistoryDTO);
    }
}
