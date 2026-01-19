package com.codewithbablu.fincore.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
// 'record' automatically creates constructor, getters, and toString. Clean!
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
      private   String id;
      private   double amount;
      private   String type;// "CREDIT" or "DEBIT"
      private   LocalDateTime timestamp;

        @Enumerated(EnumType.STRING)
        TransactionStatus status;

        public Transaction(){}

    // Custom constructor to auto-generate ID and Time
    public Transaction(String id, double amount, String type, LocalDateTime timestamp, TransactionStatus status) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
