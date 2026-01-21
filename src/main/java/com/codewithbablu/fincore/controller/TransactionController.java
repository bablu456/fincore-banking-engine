package com.codewithbablu.fincore.controller;


import com.codewithbablu.fincore.common.ApiResponse;
import com.codewithbablu.fincore.dto.TransactionRequest;
import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<Transaction>> create(@RequestBody @Valid TransactionRequest request){
        Transaction txn = service.createTransaction(request);

        return ResponseEntity.ok(ApiResponse.success("Transaction Accepted", txn));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaction>>> getAll(){
        List<Transaction> list = service.getAllTransactions();
        return ResponseEntity.ok(ApiResponse.success("Fetched All Transaction", list));
    }


    @PostMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatus(){
        Map<String, Object> stats = service.getDashboardStats();

        return ResponseEntity.ok(ApiResponse.success("Dashboard Stats Loaded",stats));
    }

    @GetMapping("/simulate-fail")
    public ResponseEntity<ApiResponse<Transaction>> createFailed(@RequestBody double amount){
        Transaction txn = service.forceFailTransaction(amount);
        return ResponseEntity.ok(ApiResponse.success("Simulated Failure Created",txn));

    }
}
