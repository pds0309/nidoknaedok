package com.service.bookshop;

import com.dto.bookshop.BookShopHistoryDTO;
import com.dto.bookshop.BookShopHistoryVO;

import java.util.List;
import java.util.Map;

public interface BookShopHistoryService {
    int submit(BookShopHistoryDTO bookShopHistoryDTO);

    BookShopHistoryDTO findOneByBookshopIdId(Map<String, Long> paramMap);

    int deleteByBookshopIdId(Map<String, Long> paramMap);

    List<BookShopHistoryVO.Member> findByBookshopId(long bookshopId);
}
