package dev.felipeoj.users_crud.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.events.exchange}")
    private String exchangeName;

    @Value("${app.events.routing-key.user-created}")
    private String userCreatedRoutingKey;

    @Value("${app.events.routing-key.user-login}")
    private String userLoginRoutingKey;

    public void publishUserCreated(String userName, String userEmail, Instant createdAt) {

        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("userName is required for user creation event");
        }
        if(userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("userEmail is required for user creation event");
        }
        if(createdAt == null) {
            throw new IllegalArgumentException("createdAt is required for user creation event");
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("eventType", "USER_CREATED");
        payload.put("occurredAt", Instant.now());

        Map<String, Object> data = new HashMap<>();
        data.put("userName", userName);
        data.put("userEmail", userEmail);
        data.put("createdAt", createdAt);
        payload.put("data", data);

        MessagePostProcessor postProcessor = message -> {
            String eventId = UUID.randomUUID().toString();
            message.getMessageProperties().setHeader("eventId", eventId);
            message.getMessageProperties().setHeader("sourceApp", "users-crud");
            message.getMessageProperties().setHeader("version", "1");
            return message;
        };

        try {
            rabbitTemplate.convertAndSend(exchangeName, userCreatedRoutingKey, payload, postProcessor);
            log.info("Event published successfully: eventType=USER_CREATED, userEmail={}", userEmail);
        } catch (Exception e) {
            log.error("Failed to publish event: eventType=USER_CREATED, userEmail={}", userEmail, e);
        }
    }



    public void publishUserLogin(String userName, String userEmail, Instant timestamp) {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("userName is required for user login event");
        }
        if(userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("userEmail is required for user login event");
        }
        if(timestamp == null) {
            throw new IllegalArgumentException("timestamp is required for user login event");
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("eventType", "LOGIN");
        payload.put("occurredAt", Instant.now());

        Map<String, Object> data = new HashMap<>();
        data.put("userName", userName);
        data.put("userEmail", userEmail);
        data.put("timestamp", timestamp);
        payload.put("data", data);

        MessagePostProcessor postProcessor = message -> {
            String eventId = UUID.randomUUID().toString();
            message.getMessageProperties().setHeader("eventId", eventId);
            message.getMessageProperties().setHeader("sourceApp", "users-crud");
            message.getMessageProperties().setHeader("version", "1");
            return message;
        };
        try {
            rabbitTemplate.convertAndSend(exchangeName, userLoginRoutingKey, payload, postProcessor);
            log.info("Event published successfully: eventType=LOGIN, userEmail={}", userEmail);
        } catch (Exception e) {
            log.error("Failed to publish event: eventType=LOGIN, userEmail={}", userEmail, e);
        }
    }
}
