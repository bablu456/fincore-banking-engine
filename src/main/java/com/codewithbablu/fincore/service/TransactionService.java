package com.codewithbablu.fincore.service;

import com.codewithbablu.fincore.model.Transaction;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutorService;

@Service
public class TransactionService {

    private final ExecutorService taskExecutor;

    public TransactionService(ExecutorService taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public Transaction createTransaction(double amount, String type){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Transaction txn = new Transaction(amount, type);

        // Logic 2 : Async Processing (Fire and Forget)
        taskExecutor.submit(() ->{
            System.out.println("Processing " + txn.id() + " On Thread "+Thread.currentThread());
            try{
                Thread.sleep(1000);
                System.out.println("Completed " + txn.id());
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        });
        return txn;
    }
}
