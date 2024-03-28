package com.example.transactionchecker.transaction.service;

import com.example.transactionchecker.kafka.KafkaComparisonResultSender;
import com.example.transactionchecker.messaging.ComparisonResultMessage;
import com.example.transactionchecker.messaging.TransactionMessage;
import com.example.transactionchecker.transaction.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TransactionProcessorService {

    private final TransactionService transactionService;
    private final KafkaComparisonResultSender kafkaComparisonResultSender;

    @Autowired
    public TransactionProcessorService(TransactionService transactionService, KafkaComparisonResultSender kafkaComparisonResultSender) {
        this.transactionService = transactionService;
        this.kafkaComparisonResultSender = kafkaComparisonResultSender;
    }

    public ComparisonResultMessage processTransactionMessage(TransactionMessage transactionMessage) {
        // Retrieve the stored transaction from Redis
        Transaction storedTransaction = transactionService.getTransaction(String.valueOf(transactionMessage.getPID()));

        // Compare received transaction amount with stored transaction amount
        return compareTransactions(transactionMessage, storedTransaction);
    }

    private ComparisonResultMessage compareTransactions(TransactionMessage transactionMessage, Transaction storedTransaction) {
        if (storedTransaction != null) {
            double storedAmount = storedTransaction.getAmount();

            String comparisonResult = getComparisonResultText(transactionMessage.getPAMOUNT(), storedAmount);
            // Prepare and send comparison result to Kafka producer
            return new ComparisonResultMessage(
                    transactionMessage.getPID(), getComparisonResultProperties(transactionMessage.getPAMOUNT(), storedAmount, comparisonResult)
            );
        } else {
            // Prepare and send comparison result to Kafka producer
            return new ComparisonResultMessage(
                    transactionMessage.getPID(), getComparisonResultProperties(transactionMessage.getPAMOUNT(), Double.NaN, "There is no stored amount for this transaction")
            );
        }
    }

    private Map<String, Object> getComparisonResultProperties(double receivedAmount, Double storedAmount, String comparisonResult){
        Map<String, Object> resultProperties = new LinkedHashMap<>();
        resultProperties.put("Received Amount", receivedAmount);
        resultProperties.put("Stored Amount", storedAmount);
        resultProperties.put("Comparison Result", comparisonResult);
        return resultProperties;
    }

    private String getComparisonResultText(double receivedAmount, double storedAmount) {
        if (receivedAmount == storedAmount) {
            return "Amounts are equal.";
        } else if (receivedAmount > storedAmount) {
            return "Received amount is greater than the stored one.";
        } else {
            return "Received amount is less than the stored one.";
        }
    }
}
