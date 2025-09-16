package dev.felipeoj.users_crud.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${app.events.exchange}")
    private String exchangeName;

    @Value("${app.events.routing-key.user-created}")
    private String userCreatedRoutingKey;

    @Value("${app.events.routing-key.user-login}")
    private String userLoginRoutingKey;

    @Bean
    public TopicExchange notificationsExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
