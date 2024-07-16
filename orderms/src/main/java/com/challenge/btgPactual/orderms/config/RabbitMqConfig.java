package com.challenge.btgPactual.orderms.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String ORDER_CREATED_QUEUE = "btg-pactual-order-created";

    @Bean
    public Declarable orderCreatedQueue() { // CRIACAO DA FILA
        return new Queue(ORDER_CREATED_QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
