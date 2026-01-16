package com.codewithbablu.fincore.model;

import java.time.LocalDateTime;
import java.util.UUID;

// 'record' automatically creates constructor, getters, and toString. Clean!
public record Transaction(
        String id,
        double amount,
        String type, // "CREDIT" or "DEBIT"
        LocalDateTime timestamp,
        TransactionStatus status
) {
    // Custom constructor to auto-generate ID and Time
    public Transaction( double amount, String type) {
        this(UUID.randomUUID().toString(), amount, type, LocalDateTime.now(),TransactionStatus.PENDING);
    }

    public Transaction withStatus(TransactionStatus newStatus){
        return new Transaction(id,amount,type,timestamp,newStatus);
    }
}