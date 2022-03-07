package com.dao.bookshop;

import com.dto.bookshop.BookShopDTO;
import org.apache.ibatis.session.SqlSession;

public class BookShopDAO {
    private static final String DIR = "com.config.BookShopMapper.";

    public int submit(SqlSession session, BookShopDTO bookShopDTO) {
        return session.insert(DIR + "submit", bookShopDTO);
    }
}
