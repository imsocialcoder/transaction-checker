package com.example.transactionchecker.kafka;

import com.example.transactionchecker.messaging.ComparisonResultMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaComparisonResultSenderTest {

    @Mock
    private KafkaTemplate<String, ComparisonResultMessage> kafkaTemplateMock;

    @InjectMocks
    private KafkaComparisonResultSender kafkaComparisonResultSender;

    @Test
    void testSendComparisonResult() {
        // Given
        Map<String, Object> comparisonResultPropertiesMock = new HashMap<>();
        comparisonResultPropertiesMock.put("keyMock", "valueMock");
        ComparisonResultMessage comparisonResultMessageMock = new ComparisonResultMessage(1, comparisonResultPropertiesMock);

        // When
        kafkaComparisonResultSender.sendComparisonResult(comparisonResultMessageMock);

        // Then
        verify(kafkaTemplateMock).send("transaction-comparison", comparisonResultMessageMock);
    }
}

