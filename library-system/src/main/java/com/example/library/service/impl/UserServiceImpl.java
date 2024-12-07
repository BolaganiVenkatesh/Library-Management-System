package com.example.library.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.dto.BookDTO;
import com.example.library.dto.UserDTO;
import com.example.library.entity.User;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    
    @Override
    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        return userRepository.save(user);
    }

    @Override
    public UserDTO getUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getBorrowedBooks().stream()
                        .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    List<BookDTO> borrowedBooks = user.getBorrowedBooks().stream()
                            .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor()))
                            .collect(Collectors.toList());
                    return new UserDTO(user.getId(), user.getName(), borrowedBooks);
                })
                .collect(Collectors.toList());
    }
   
}
