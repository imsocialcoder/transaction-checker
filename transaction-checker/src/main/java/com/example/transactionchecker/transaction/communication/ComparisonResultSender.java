package com.example.transactionchecker.transaction.communication;

import com.example.transactionchecker.messaging.ComparisonResultMessage;

public interface ComparisonResultSender {
    void sendComparisonResult(ComparisonResultMessage comparisonResultMessage);
}
