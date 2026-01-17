package com.codewithbablu.fincore.controller;


import com.codewithbablu.fincore.dto.TransactionRequest;
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
    public Transaction create(@RequestBody TransactionRequest request){
        return service.createTransaction(request);
    }

    @GetMapping
    public List<Transaction> getAll(){
        return service.getAllTransactions();
    }

    @PostMapping("/simulate-fail")
    public Transaction createFakeFailed(@RequestParam double amount) {
        Transaction txn = new Transaction(amount, "TEST_FAIL");
        // Zabardasti FAILED status set kar diya
        Transaction failedTxn = txn.withStatus(com.codewithbablu.fincore.model.TransactionStatus.FAILED);
        // Repository access karne ke liye service me method chahiye hoga,
        // par abhi ke liye hum service ke through hi save karwa sakte hain agar method ho.
        // Chalo simple rakhte hain, hum Service me ek naya method banate hain.
        return service.forceFailTransaction(amount);
    }

}
