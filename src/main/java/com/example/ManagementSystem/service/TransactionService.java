package com.example.ManagementSystem.service;

import com.example.ManagementSystem.dto.Response;
import com.example.ManagementSystem.dto.TransactionRequest;
import com.example.ManagementSystem.enums.TransactionStatus;

public interface TransactionService {
    
    Response purchase(TransactionRequest transactionRequest);
    
    Response sell(TransactionRequest transactionRequest);

    Response returnToSupllier(TransactionRequest transactionRequest);

    Response getAllTransactions(int page, int size, String filter);

    Response getAllTransactionById(Long id);

    Response getAllTransactionByMonthAndYear(int month, int year);

    Response updateTransactionStatus(Long transactionId, TransactionStatus status);


}
