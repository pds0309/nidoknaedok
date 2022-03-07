package com.dao.book;

import com.dto.book.BookDTO;
import org.apache.ibatis.session.SqlSession;

public class BookDAO {
    private static final String DIR = "com.config.BookMapper.";

    public BookDTO findById(SqlSession session, long bookId) {
        return session.selectOne(DIR + "findById", bookId);
    }

    public int submit(SqlSession session, BookDTO bookDTO) {
        return session.insert(DIR + "submit", bookDTO);
    }
}
