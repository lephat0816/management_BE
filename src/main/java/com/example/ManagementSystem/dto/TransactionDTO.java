package com.example.ManagementSystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.ManagementSystem.enums.TransactionStatus;
import com.example.ManagementSystem.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

    private Long id;

    private Integer totalProducts;
    private BigDecimal totalPrice;

    private TransactionType transactionType; // purchase, sale, return
    private TransactionStatus status; // pending, completed, processcing

    private String description;
    private String note;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

   
    private ProductDTO product;
    private UserDTO user;
    private SupplierDTO supplier;
   
    
}
