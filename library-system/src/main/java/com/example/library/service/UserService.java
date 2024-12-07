package com.example.library.service;

import java.util.List;

import com.example.library.dto.UserDTO;
import com.example.library.entity.User;

public interface UserService {
    User createUser(String name);
    UserDTO getUserDetails(Long userId);
    List<UserDTO> getAllUsers();
}
