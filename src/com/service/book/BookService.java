package com.service.book;

import com.dto.book.BookDTO;

public interface BookService {
    BookDTO findById(long bookId);

    int submit(BookDTO bookDTO);
}
