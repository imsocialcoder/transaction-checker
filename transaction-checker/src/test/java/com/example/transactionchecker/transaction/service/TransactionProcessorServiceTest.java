package com.example.transactionchecker.transaction.service;

import com.example.transactionchecker.messaging.ComparisonResultMessage;
import com.example.transactionchecker.messaging.TransactionMessage;
import com.example.transactionchecker.transaction.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionProcessorServiceTest {

    @Mock
    private TransactionService transactionServiceMock;

    @InjectMocks
    private TransactionProcessorService transactionProcessorService;

    private final String STORED_AMOUNT = "Stored Amount";
    private final String RECEIVED_AMOUNT = "Received Amount";
    private final String COMPARISON_RESULT = "Comparison Result";

    @Test
    void testProcessTransactionMessageWithStoredTransactionGreaterThan() {
        // Given
        Transaction storedTransactionMock = new Transaction();
        storedTransactionMock.setAmount(100.0); // Set the amount for the stored transaction
        TransactionMessage transactionMessageMock = new TransactionMessage(123, 150.0, 123456789);

        // When
        when(transactionServiceMock.getTransaction(any())).thenReturn(storedTransactionMock);
        ComparisonResultMessage resultMessage = transactionProcessorService.processTransactionMessage(transactionMessageMock);
        Map<String, Object> comparisonResultProperties = resultMessage.getComparisonResultProperties();

        // Then
        assertEquals(123, resultMessage.getTransactionId());
        assertEquals("Received amount is greater than the stored one.", comparisonResultProperties.get(COMPARISON_RESULT));
        assertEquals(150.0, comparisonResultProperties.get(RECEIVED_AMOUNT));
        assertEquals(100.0, comparisonResultProperties.get(STORED_AMOUNT));
    }

    @Test
    void testProcessTransactionMessageWithStoredTransactionLessThan() {
        // Given
        Transaction storedTransactionMock = new Transaction();
        storedTransactionMock.setAmount(100.0); // Set the amount for the stored transaction
        TransactionMessage transactionMessageMock = new TransactionMessage(123, 99.0, 123456789);

        // When
        when(transactionServiceMock.getTransaction(any())).thenReturn(storedTransactionMock);
        ComparisonResultMessage resultMessage = transactionProcessorService.processTransactionMessage(transactionMessageMock);
        Map<String, Object> comparisonResultProperties = resultMessage.getComparisonResultProperties();

        // Then
        assertEquals(123, resultMessage.getTransactionId());
        assertEquals("Received amount is less than the stored one.", comparisonResultProperties.get(COMPARISON_RESULT));
        assertEquals(99.0, comparisonResultProperties.get(RECEIVED_AMOUNT));
        assertEquals(100.0, comparisonResultProperties.get(STORED_AMOUNT));
    }

    @Test
    void testProcessTransactionMessageWithNoStoredTransaction() {
        // Given
        TransactionMessage transactionMessageMock = new TransactionMessage(123, 150.0, 123456789);

        // When
        when(transactionServiceMock.getTransaction(any())).thenReturn(null);
        ComparisonResultMessage resultMessage = transactionProcessorService.processTransactionMessage(transactionMessageMock);
        Map<String, Object> comparisonResultProperties = resultMessage.getComparisonResultProperties();

        // Then
        assertEquals(123, resultMessage.getTransactionId());
        assertEquals("There is no stored amount for this transaction", comparisonResultProperties.get(COMPARISON_RESULT));
        assertEquals(150.0, comparisonResultProperties.get(RECEIVED_AMOUNT));
        assertEquals(Double.NaN, comparisonResultProperties.get(STORED_AMOUNT));
    }

    @Test
    void testProcessTransactionMessageWithEqualAmounts() {
        // Given
        Transaction storedTransactionMock = new Transaction();
        double amount = 150.0;
        storedTransactionMock.setAmount(amount);
        TransactionMessage transactionMessageMock = new TransactionMessage(123, amount, 123456789);

        // When
        when(transactionServiceMock.getTransaction(any())).thenReturn(storedTransactionMock);
        ComparisonResultMessage resultMessage = transactionProcessorService.processTransactionMessage(transactionMessageMock);
        Map<String, Object> comparisonResultProperties = resultMessage.getComparisonResultProperties();

        // Then
        assertEquals(123, resultMessage.getTransactionId());
        assertEquals("Amounts are equal.", comparisonResultProperties.get(COMPARISON_RESULT));
        assertEquals(amount, comparisonResultProperties.get(RECEIVED_AMOUNT));
        assertEquals(amount, comparisonResultProperties.get(STORED_AMOUNT));
    }
}

