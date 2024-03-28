package com.example.transactionchecker.transaction.model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private double amount;
    private Metadata metadata;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    static class Metadata implements Serializable {
        private int originatorId;
        private int destinationId;

        public int getOriginatorId() {
            return originatorId;
        }

        public void setOriginatorId(int originatorId) {
            this.originatorId = originatorId;
        }

        public int getDestinationId() {
            return destinationId;
        }

        public void setDestinationId(int destinationId) {
            this.destinationId = destinationId;
        }
    }
}
