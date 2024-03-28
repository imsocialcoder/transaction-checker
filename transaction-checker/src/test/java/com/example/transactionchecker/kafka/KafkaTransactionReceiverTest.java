package com.example.transactionchecker.kafka;

import com.example.transactionchecker.messaging.ComparisonResultMessage;
import com.example.transactionchecker.messaging.TransactionMessage;
import com.example.transactionchecker.transaction.service.TransactionProcessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaTransactionReceiverTest {

    @Mock
    private TransactionProcessorService transactionProcessorServiceMock;

    @Mock
    private KafkaComparisonResultSender kafkaComparisonResultSenderMock;

    @InjectMocks
    private KafkaTransactionReceiver kafkaTransactionReceiver;

    @Test
    void testReceiveTransactions() {
        // Given
        TransactionMessage[] transactionMessagesMock = {
                new TransactionMessage(1, 100.0, 123456789),
                new TransactionMessage(2, 200.0, 123456789)
        };

        ComparisonResultMessage comparisonResultMessageMock = new ComparisonResultMessage(1, null);

        // When
        when(transactionProcessorServiceMock.processTransactionMessage(any())).thenReturn(comparisonResultMessageMock);
        kafkaTransactionReceiver.receiveTransactions(transactionMessagesMock);

        // Then
        verify(transactionProcessorServiceMock).processTransactionMessage(transactionMessagesMock[0]);
        verify(transactionProcessorServiceMock).processTransactionMessage(transactionMessagesMock[1]);
        verify(kafkaComparisonResultSenderMock, times(2)).sendComparisonResult(any());

    }

}

