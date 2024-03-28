package com.example.transactionchecker.transaction.communication;

import com.example.transactionchecker.messaging.TransactionMessage;

public interface TransactionReceiver {
    void receiveTransactions(TransactionMessage[] transactionMessages);
}
