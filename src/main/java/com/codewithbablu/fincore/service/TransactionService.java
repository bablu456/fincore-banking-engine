package com.codewithbablu.fincore.service;

import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class TransactionService {

    private final ExecutorService taskExecutor;
    private final TransactionRepository repository;

    public TransactionService(ExecutorService taskExecutor, TransactionRepository repository) {
        this.repository = repository;
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
                Thread.sleep(500);
                // Main Change: Ab hum data save kar rahe hain
                repository.save(txn);
                System.out.println("Completed " + txn.id());
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        });

        repository.save(txn);
        return txn;
    }

    public List<Transaction> getAllTransactions(){
        return repository.findAll();
    }

}
