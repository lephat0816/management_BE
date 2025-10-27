package com.example.ManagementSystem.service;

import com.example.ManagementSystem.dto.LoginRequest;
import com.example.ManagementSystem.dto.RegisterRequest;
import com.example.ManagementSystem.dto.Response;
import com.example.ManagementSystem.dto.UserDTO;
import com.example.ManagementSystem.model.User;

public interface UserService {
    
    Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getCurrentLoggedInUser();

    Response getUserById(Long id);

    Response updateUser(Long id, UserDTO userDTO);

    Response deleteUser(Long id);

    Response getUserTransaction(Long id);
}
