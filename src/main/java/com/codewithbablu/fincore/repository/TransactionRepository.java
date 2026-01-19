package com.codewithbablu.fincore.repository;


import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByStatus(TransactionStatus status);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = :status")
    double selectTotalAmountByStatus(@Param("status") TransactionStatus status);

    @Query("SELECT SUM(t) FROM Transaction t WHERE t.status = :status")
    double countByStatus(@Param("status") TransactionStatus status);

}
