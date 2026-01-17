package com.codewithbablu.fincore.repository;


import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.model.TransactionStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {
    // "Beast" Logic : Thread-Safe Memory Storage
    // Key = Transaction ID (String), Value = Transaction Object
    private final ConcurrentHashMap<String, Transaction> transactionStore = new ConcurrentHashMap<>();

    // 1. Save Data
    public Transaction save(Transaction transaction){
        transactionStore.put(transaction.id(),transaction);
        return transaction;
    }

    // 2. Get All Data (Values ko list Me Converte karke return karenge)
    public List<Transaction> findAll(){
        return new ArrayList<>(transactionStore.values());
    }

    // 3. Find by ID (Optional return karenge taki NullPointerException na aaye)
    // Optional Java 8+ ka feature hai. ye box jaisa hai ye to item hoga, ya empty hoga
    public Optional<Transaction> findById(String id) {
    return Optional.ofNullable(transactionStore.get(id));
    }

    public List<Transaction> findByStatus(TransactionStatus status){
        return transactionStore.values().stream()
                .filter(txn -> txn.status() == status)
                .collect(Collectors.toList());
    }

}
