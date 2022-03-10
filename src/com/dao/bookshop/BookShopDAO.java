package com.dao.bookshop;

import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.bookshop.TradeCode;
import com.dto.common.PageDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

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

    public List<BookShopVO.Member> findAllByParams(SqlSession session, int startPage, int perPage, Map<String, Object> paramMap) {
        paramMap.put("startPage", startPage);
        paramMap.put("perPage", perPage);
        return session.selectList(DIR + "findAllByParams", paramMap);
    }
}
