package com.codewithbablu.fincore.controller;


import com.codewithbablu.fincore.dto.TransactionRequest;
import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;
    public TransactionController(TransactionService service){
        this.service  = service;
    }

    @PostMapping
    public Transaction create(@RequestBody TransactionRequest request){
        return service.createTransaction(request);
    }

    @GetMapping
    public List<Transaction> getAll(){
        return service.getAllTransactions();
    }

    @PostMapping("/simulate-fail")
    public Transaction createFakeFailed(@RequestParam double amount) {

        return service.forceFailTransaction(amount);
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getStats(){
        return service.getDashboardStats();
    }

}
