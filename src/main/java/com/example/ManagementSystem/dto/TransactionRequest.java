package com.example.ManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {
    
    @Positive(message = "product is required")
    private Long productId;
    @Positive(message = "quantity is required")
    private Integer quantity;
    
    private Long supplierId;
    private String description;
    private String note;

    // TransactionStatus status;
}
