package com.example.transactionchecker.transaction.serialization;

import com.example.transactionchecker.transaction.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import java.io.IOException;

public class TransactionRedisSerializer implements  RedisSerializer<Transaction> {

    private final ObjectMapper objectMapper;

    public TransactionRedisSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(Transaction transaction) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(transaction);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing object", e);
        }
    }

    @Override
    public Transaction deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, Transaction.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing object", e);
        }
    }
}