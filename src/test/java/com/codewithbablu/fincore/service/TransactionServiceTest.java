package com.codewithbablu.fincore.service;

// ✅ Imports (Ye sab hona jaruri hai)
import com.codewithbablu.fincore.dto.TransactionRequest;
import com.codewithbablu.fincore.model.Transaction;
import com.codewithbablu.fincore.model.TransactionStatus;
import com.codewithbablu.fincore.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// ✅ Static Imports for Testing
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private ExecutorService taskExecutor;

    @InjectMocks
    private TransactionService service;

    @Test
    void shouldCreateTransactionSuccessfully(){

        TransactionRequest request = new TransactionRequest(5000.0, "CREDIT");

        Transaction savedTxn = new Transaction();
        savedTxn.setId("123-abc");
        savedTxn.setAmount(5000.0);
        savedTxn.setStatus(TransactionStatus.PENDING);
        savedTxn.setType("CREDIT");

        when(repository.save(any(Transaction.class))).thenReturn(savedTxn);

        Transaction result = service.createTransaction(request);

        assertNotNull(result.getId(), "ID null nahi honi chahiye ");
        assertEquals(5000.0, result.getAmount(), "Amount match hona chahiye");
        assertEquals("CREDIT", result.getType(), "Type match hona chahiye");
        assertEquals(TransactionStatus.PENDING, result.getStatus(), "Status PENDING hona chahiye");
    }
}
