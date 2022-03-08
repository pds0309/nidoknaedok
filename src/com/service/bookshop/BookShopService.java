package com.service.bookshop;

import com.dto.bookshop.BookShopDTO;
import com.dto.bookshop.BookShopVO;
import com.dto.common.PageDTO;

public interface BookShopService {
    int submit(BookShopDTO bookShopDTO);

    PageDTO<BookShopVO.Book> findAllPageable(int recordAmountPerPage, int currentPage);
}
