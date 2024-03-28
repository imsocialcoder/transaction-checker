package com.example.transactionchecker.transaction.service;

import com.example.transactionchecker.transaction.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private RedisTemplate<String, Transaction> redisTemplateMock;

    @Mock
    private ValueOperations<String, Transaction> valueOperationsMock;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testGetTransaction() {
        // Given
        String transactionId = "123";
        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setAmount(100.0); // Set any values specific to your test case

        // When
        when(redisTemplateMock.opsForValue()).thenReturn(valueOperationsMock);
        when(valueOperationsMock.get(any())).thenReturn(expectedTransaction);
        Transaction resultTransaction = transactionService.getTransaction(transactionId);

        // Then
        assertEquals(expectedTransaction, resultTransaction);
    }
}

