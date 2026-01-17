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
            Transaction retryTxn = txn.withStatus(TransactionStatus.PROCESSING);
            repository.save(retryTxn);

            System.out.println(" Retrying "+txn.id());

            Transaction succesTxn = retryTxn.withStatus(TransactionStatus.SUCCESS);
            repository.save(succesTxn);
        }
    }
}
