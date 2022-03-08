package com.dao.bookshop;

import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.common.PageDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

public class BookShopDAO {
    private static final String DIR = "com.config.BookShopMapper.";

    public int submit(SqlSession session, BookShopDTO bookShopDTO) {
        return session.insert(DIR + "submit", bookShopDTO);
    }

    private int totalCountForPageable(SqlSession session) {
        return session.selectOne(DIR + "pageable");
    }

    public PageDTO<BookShopVO.Book> findAllWithPagination(SqlSession session, int currentPage, int perPage) {
        int totalCount = totalCountForPageable(session);
        return new PageDTO<>(currentPage, perPage, totalCount, session.selectList(DIR + "findAllDesc", null, new RowBounds((currentPage - 1) * perPage, perPage)));
    }
}
