package com.example.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dto.BookDTO;
import com.example.library.dto.UserDTO;
import com.example.library.entity.User;
import com.example.library.service.BookService;
import com.example.library.service.UserService;

@RestController
@RequestMapping("/api/library")
public class BookController {
	
	

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;
  
    @PostMapping("/createBook")
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @GetMapping("/availableBooks")
    public List<BookDTO> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    @PostMapping("/borrowBook")
    public String borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return bookService.borrowBook(userId, bookId);
    }

    @PostMapping("/createUser")
    public User createUser(@RequestParam String name) {
        return userService.createUser(name);
    }

    @GetMapping("/userDetails")
    public UserDTO getUserDetails(@RequestParam Long userId) {
        return userService.getUserDetails(userId);
    }
    
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/add")
    public int add(@RequestParam int num1, @RequestParam int num2) {
        return num1 + num2;
    }
    
   
}
