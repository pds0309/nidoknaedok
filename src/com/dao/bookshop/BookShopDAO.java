package com.dao.bookshop;

import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopHistoryDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.bookshop.TradeCode;
import com.dto.common.PageDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<BookShopVO.Member> findByBookshopId(SqlSession session, long bookshopId) {
        return Optional.ofNullable(session.selectOne(DIR + "findByBookshopId", bookshopId));
    }

    public int updateBookShopInfo(SqlSession session, BookShopDTO bookShopDTO) {
        return session.update(DIR + "updateBookShopInfo", bookShopDTO);
    }

    public PageDTO<BookShopVO.Book> findAllByMemberId(SqlSession session, Map<String, Long> paramMap, int currentPage, int perPage) {
        return new PageDTO<>(currentPage, perPage, 0,
                session.selectList(DIR + "findAllByMemberId", paramMap, new RowBounds((currentPage - 1) * perPage, perPage)));
    }

    public Map<String, Integer> findBookShopStatsByMemberId(SqlSession session, long memberId) {
        return session.selectOne(DIR + "findBookShopStatsByMemberId", memberId);
    }

    public int updateSellStatus(SqlSession session, BookShopHistoryDTO bookShopHistoryDTO) {
        return session.update(DIR + "updateSellStatus", bookShopHistoryDTO);
    }
}
