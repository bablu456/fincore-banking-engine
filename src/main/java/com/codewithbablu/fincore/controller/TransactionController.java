package com.codewithbablu.fincore.controller;


import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;
    public TransactionController(TransactionService service){
        this.service  = service;
    }

    @PostMapping
    public Transaction create(@RequestParam double amount, @RequestParam String type){
        return service.createTransaction(amount, type);
    }

    @GetMapping
    public List<Transaction> getAll(){
        return service.getAllTransactions();
    }
}
