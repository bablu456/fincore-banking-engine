package com.codewithbablu.fincore.service;


import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.model.TransactionStatus;
import com.codewithbablu.fincore.repository.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCleanupService {

    private final TransactionRepository repository;

    public TransactionCleanupService(TransactionRepository repository){
        this.repository = repository;
    }

    @Scheduled(fixedRate = 10000)
    public void retryFailedTransactions(){

        List<Transaction> failedTxns = repository.findByStatus(TransactionStatus.FAILED);

        if(failedTxns.isEmpty()){
            return;
        }

        System.out.println(" Scheduler Found "+ failedTxns.size()+" failed transactions. Retrying...");

        for(Transaction txn : failedTxns){

            System.out.println("⚠️ Scheduler Found " + failedTxns.size() + " failed transactions.");

            txn.setStatus(TransactionStatus.PROCESSING);
            repository.save(txn);

            txn.setStatus(TransactionStatus.SUCCESS); // Setter
            repository.save(txn);
        }
    }
}
