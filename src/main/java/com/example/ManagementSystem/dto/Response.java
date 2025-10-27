package com.example.ManagementSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.ManagementSystem.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    // Generic
    private int status;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    // for login
    private String token;
    private UserRole role;
    private String expirationTime;

    // for pagination
    private Integer totalPages;
    private Long totalElements;

    // data output optionals
    private UserDTO user;
    private List<UserDTO> users;

    private SupplierDTO supplier;
    private List<SupplierDTO> suppliers;

    private CategoryDTO category;
    private List<CategoryDTO> categories;

    private ProductDTO product;
    private List<ProductDTO> products;

    private TransactionDTO transaction;
    private List<TransactionDTO> transactions;

}
