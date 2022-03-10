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
}
