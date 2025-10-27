package com.example.ManagementSystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.ManagementSystem.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
@Data
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalProducts;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // purchase, sale, return
    @Enumerated(EnumType.STRING)
    private com.example.ManagementSystem.enums.TransactionStatus status; // pending, completed, processcing

    private String description;
    private String note;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @Override
    public String toString() {
        return "Transaction [id=" + id + ", totalProducts=" + totalProducts + ", totalPrice=" + totalPrice
                + ", transactionType=" + transactionType + ", status=" + status + ", description=" + description
                + ", note=" + note + ", createdAt=" + createdAt + ", updateAt=" + updateAt + "]";
    }
    
}
