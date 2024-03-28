package com.example.transactionchecker.kafka;

import com.example.transactionchecker.messaging.ComparisonResultMessage;
import com.example.transactionchecker.transaction.communication.ComparisonResultSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaComparisonResultSender implements ComparisonResultSender {

    private final KafkaTemplate<String, ComparisonResultMessage> kafkaTemplate;

    @Autowired
    public KafkaComparisonResultSender(KafkaTemplate<String, ComparisonResultMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendComparisonResult(ComparisonResultMessage comparisonResult) {
        kafkaTemplate.send("transaction-comparison", comparisonResult);
    }
}
