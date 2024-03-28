package com.example.transactionchecker.transaction.serialization;

import com.example.transactionchecker.transaction.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TransactionRedisSerializerTest {

    @Mock
    private ObjectMapper objectMapperMock;

    @InjectMocks
    private TransactionRedisSerializer serializer;

    @Test
    void serializeTransaction_Success() throws JsonProcessingException {
        // Given
        Transaction transactionMock = new Transaction();
        byte[] expectedBytes = "SerializedData".getBytes();

        // When
        when(objectMapperMock.writeValueAsBytes(transactionMock)).thenReturn(expectedBytes);
        byte[] resultBytes = serializer.serialize(transactionMock);

        // Then
        assertArrayEquals(expectedBytes, resultBytes);
    }

    @Test
    void deserializeTransaction_Success() throws IOException {
        // Given
        byte[] serializedBytes = "SerializedData".getBytes();
        Transaction expectedTransaction = new Transaction();

        // When
        when(objectMapperMock.readValue(serializedBytes, Transaction.class)).thenReturn(expectedTransaction);
        Transaction resultTransaction = serializer.deserialize(serializedBytes);

        // Then
        assertNotNull(resultTransaction);
        assertEquals(expectedTransaction, resultTransaction);
    }

    @Test
    void serializeTransaction_WithException() throws JsonProcessingException {
        // Given
        Transaction transactionMock = new Transaction();

        //When
        when(objectMapperMock.writeValueAsBytes(transactionMock)).thenThrow(JsonProcessingException.class);

        // Then
        assertThrows(SerializationException.class, () -> serializer.serialize(transactionMock));
    }

    @Test
    void deserializeTransaction_WithException() throws IOException {
        // Given
        byte[] serializedBytes = "SerializedData".getBytes();

        //When
        when(objectMapperMock.readValue(serializedBytes, Transaction.class)).thenThrow(IOException.class);

        // Then
        assertThrows(SerializationException.class, () -> serializer.deserialize(serializedBytes));
    }
}