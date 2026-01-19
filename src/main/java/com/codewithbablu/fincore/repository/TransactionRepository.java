package com.codewithbablu.fincore.repository;


import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByStatus(TransactionStatus status);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = 'SUCCESS'")
    double selectTotalSuccessAmount();

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = 'FAILED'")
    double countFailedTransaction();

}
