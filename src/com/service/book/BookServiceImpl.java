package com.service.book;

import com.config.MySqlSessionFactory;
import com.dao.book.BookDAO;
import com.dto.book.BookDTO;
import org.apache.ibatis.session.SqlSession;

public class BookServiceImpl implements BookService {
    private static final BookDAO bookDAO = new BookDAO();

    @Override
    public BookDTO findById(long bookId) {
        SqlSession session = MySqlSessionFactory.getSession();
        try {
            return bookDAO.findById(session, bookId);
        } finally {
            session.close();
        }
    }

    @Override
    public int submit(BookDTO bookDTO) {
        SqlSession session = MySqlSessionFactory.getSession();
        int status = 0;
        try {
            status = bookDAO.submit(session, bookDTO);
            session.commit();
        } finally {
            session.close();
        }
        return status;
    }
}
