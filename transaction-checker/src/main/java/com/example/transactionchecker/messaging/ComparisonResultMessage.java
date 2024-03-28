package com.example.transactionchecker.messaging;

import java.io.Serializable;
import java.util.Map;

public class ComparisonResultMessage implements Serializable {
    private int transactionId;
    private Map<String, Object> comparisonResultProperties;

    public ComparisonResultMessage(int transactionId, Map<String, Object> comparisonResultProperties) {
        this.transactionId = transactionId;
        this.comparisonResultProperties = comparisonResultProperties;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Map<String, Object> getComparisonResultProperties() {
        return comparisonResultProperties;
    }

    public void setComparisonResultProperties(Map<String, Object> comparisonResultProperties) {
        this.comparisonResultProperties = comparisonResultProperties;
    }
}