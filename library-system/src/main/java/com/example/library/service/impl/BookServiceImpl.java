package com.example.library.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.exception.BookNotAvailableException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.BookService;

import lombok.extern.slf4j.Slf4j;
@Slf4j

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
  
    @Override
    public BookDTO createBook(BookDTO bookDTO) {
      // Convert DTO to Entity
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setAvailable(true); // Set the book as available by default
        // Save book in the database
        book = bookRepository.save(book);
        // Convert back to DTO for response
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
    }
    
    @Override
    public List<BookDTO> getAvailableBooks() {
        List<Book> books = bookRepository.findByAvailableTrue();
        return books.stream()
                    .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor()))
                    .collect(Collectors.toList());
    }

    @Override
    public String borrowBook(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + bookId + " not found"));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book with ID " + bookId + " is currently unavailable");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        book.setAvailable(false);
        user.getBorrowedBooks().add(book);
        bookRepository.save(book);
        userRepository.save(user);

        return "Book borrowed successfully";
    }

   
}
