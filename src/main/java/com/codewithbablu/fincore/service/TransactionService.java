package com.codewithbablu.fincore.service;

import com.codewithbablu.fincore.dto.TransactionRequest;
import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.model.TransactionStatus;
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

    public Transaction createTransaction(TransactionRequest request){
        // 1. Validation
        if (request.amount()<=0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        // 2. Convert DTO to Entity (Status = Pending initially
        Transaction txn = new Transaction(request.amount(), request.type());


        repository.save(txn);

        taskExecutor.submit(() -> {
            try{
                Transaction processingTxn = txn.withStatus(TransactionStatus.PROCESSING);
                repository.save(processingTxn);

                Thread.sleep(2000);

                Transaction successTxn = processingTxn.withStatus(TransactionStatus.SUCCESS);
                repository.save(successTxn);

                System.out.println("Transaction Completed: "+txn.id());
            }catch (Exception e){
                Transaction failedTxn = txn.withStatus(TransactionStatus.FAILED);
                repository.save(failedTxn);
                System.out.println(" Transaction Failed : "+txn.id());
            }
        });

        return txn;
    }

    public List<Transaction> getAllTransactions(){
        return repository.findAll();
    }

    // ... class ke andar ...
    public Transaction forceFailTransaction(double amount) {
        Transaction txn = new Transaction(amount, "TEST_FAIL");
        Transaction failed = txn.withStatus(TransactionStatus.FAILED);
        repository.save(failed);
        return failed;
    }

}
