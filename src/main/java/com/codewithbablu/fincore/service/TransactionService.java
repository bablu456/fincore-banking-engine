package com.codewithbablu.fincore.service;

import com.codewithbablu.fincore.dto.TransactionRequest;
import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.model.TransactionStatus;
import com.codewithbablu.fincore.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class TransactionService {

    private final ExecutorService taskExecutor;
    private final TransactionRepository repository;

    public TransactionService(ExecutorService taskExecutor, TransactionRepository repository) {
        this.repository = repository;
        this.taskExecutor = taskExecutor;
    }

    public Transaction createTransaction(TransactionRequest request){

        Transaction txn = new Transaction(
                UUID.randomUUID().toString(),
                request.amount(),
                request.type(),
                LocalDateTime.now(),
                TransactionStatus.PENDING
        );
        repository.save(txn);

        taskExecutor.submit(() -> {
            try{
                txn.setStatus(TransactionStatus.PROCESSING);
                repository.save(txn);

                Thread.sleep(2000);

                txn.setStatus(TransactionStatus.SUCCESS);
                repository.save(txn);

                System.out.println("Transaction Completed: "+txn.getId());
            }catch (Exception e){
                txn.setStatus(TransactionStatus.FAILED);
                repository.save(txn);
                System.out.println("Transaction Failed : "+txn.getId());
            }
        });
        return txn;
    }


}
