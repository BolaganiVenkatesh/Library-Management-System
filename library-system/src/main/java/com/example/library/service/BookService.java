package com.example.library.service;

import java.util.List;

import com.example.library.dto.BookDTO;



public interface BookService {
	
	BookDTO createBook(BookDTO bookDTO);

    List<BookDTO> getAvailableBooks();
    String borrowBook(Long userId, Long bookId);
}
