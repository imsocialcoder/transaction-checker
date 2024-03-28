package com.example.transactionchecker.kafka;

import com.example.transactionchecker.messaging.ComparisonResultMessage;
import com.example.transactionchecker.messaging.TransactionMessage;
import com.example.transactionchecker.transaction.service.TransactionProcessorService;
import com.example.transactionchecker.transaction.communication.TransactionReceiver;
import com.example.transactionchecker.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaTransactionReceiver implements TransactionReceiver {

    private final KafkaComparisonResultSender kafkaComparisonResultSender;
    private final TransactionProcessorService transactionProcessorService;

    @Autowired
    public KafkaTransactionReceiver(TransactionService transactionService, KafkaComparisonResultSender kafkaComparisonResultSender, TransactionProcessorService transactionProcessorService) {
        this.transactionProcessorService = transactionProcessorService;
        this.kafkaComparisonResultSender = kafkaComparisonResultSender;
    }

    @KafkaListener(topics = "transaction-input", groupId = "transaction-checker-group")
    @Override
    public void receiveTransactions(TransactionMessage[] transactionMessages) {
        for (TransactionMessage transactionMessage : transactionMessages) {
            ComparisonResultMessage comparisonResultMessage = transactionProcessorService.processTransactionMessage(transactionMessage);
            kafkaComparisonResultSender.sendComparisonResult(comparisonResultMessage);
        }
    }
}
