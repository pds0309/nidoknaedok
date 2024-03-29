package com.service.bookshop;

import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.common.PageDTO;

import java.util.List;
import java.util.Map;

public interface BookShopService {
    int submit(BookShopDTO bookShopDTO);

    PageDTO<BookShopVO.Book> findAllPageable(int recordAmountPerPage, int currentPage);

    List<BookShopVO.Member> findAllByParams(Map<String, Object> paramMap, int recordAmountPerPage, int startPage);

    BookShopVO.Member findByBookshopId(long bookshopId);

    int updateBookShopInfo(BookShopDTO bookShopDTO, long sessionMemberId);

    PageDTO<BookShopVO.Book> findAllByMemberId(Map<String, Long> paramMap, int recordAmountPerPage, int currentPage);

    Map<String, Integer> findBookShopStatsByMemberId(long memberId);

    int deleteByBookshopId(long bookshopId);
}
