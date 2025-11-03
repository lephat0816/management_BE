package com.example.ManagementSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.TypeToken;

import com.example.ManagementSystem.dto.LoginRequest;
import com.example.ManagementSystem.dto.RegisterRequest;
import com.example.ManagementSystem.dto.Response;
import com.example.ManagementSystem.dto.TransactionDTO;
import com.example.ManagementSystem.dto.UserDTO;
import com.example.ManagementSystem.enums.UserRole;
import com.example.ManagementSystem.exception.InvalidCredentialsException;
import com.example.ManagementSystem.exception.NotFoundException;
import com.example.ManagementSystem.model.User;
import com.example.ManagementSystem.repository.UserRepository;
import com.example.ManagementSystem.security.JwtUtils;
import com.example.ManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils JwtUtils;

    @Override
    public Response registerUser(RegisterRequest registerRequest) {

        UserRole role = UserRole.MANAGER;
        if (registerRequest.getRole() != null) {
            role = registerRequest.getRole();
        }
        User userToSave = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(role)
                .build();
        userRepository.save(userToSave);
        return Response.builder()
                .status(200)
                .message("User was successfully register")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email Not Found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password Does Not Match");
        }

        String token = JwtUtils.generateToken(user.getEmail());
        return Response.builder()
                .status(200)
                .message("User Logged in Successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 months")
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        users.forEach(user -> user.setTransactions(null));
        List<UserDTO> userDTOs = modelMapper.map(users, new TypeToken<List<UserDTO>>() {
        }.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .users(userDTOs)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User Not Found"));

        user.setTransactions(null);
        return user;
    }

    @Override
    public Response getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        if (userDTO.getEmail() != null)
            existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPhoneNumber() != null)
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getName() != null)
            existingUser.setName(userDTO.getName());
        if (userDTO.getRole() != null)
            existingUser.setRole(userDTO.getRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existingUser);
        return Response.builder()
                .status(200)
                .message("User Successfully updated")
                .build();
    }

    @Override
    public Response deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        userRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("User Successfully Deleted")
                .build();
    }

    @Override
    public Response getUserTransaction(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        List<TransactionDTO> transactionDTOs = user.getTransactions().stream()
                .map((transaction -> {
                    TransactionDTO transactionDTO = modelMapper.map((transaction), TransactionDTO.class);
                    transactionDTO.setUser(null);
                    return transactionDTO;
                })).collect(Collectors.toList());
        userDTO.setTransactions(transactionDTOs);

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
    }

}
