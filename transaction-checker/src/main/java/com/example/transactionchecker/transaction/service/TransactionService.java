package com.example.transactionchecker.transaction.service;

import com.example.transactionchecker.transaction.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final RedisTemplate<String, Transaction> redisTemplate;

    @Autowired
    public TransactionService(RedisTemplate<String, Transaction> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Transaction getTransaction(String transactionId) {
        return redisTemplate.opsForValue().get(transactionId);
    }
}
