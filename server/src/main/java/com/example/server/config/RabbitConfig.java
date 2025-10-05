package com.example.server.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "reliefData";
    public static final String EXCHANGE_NAME = "priority";

    public static final String ASSIGNEE_EXCHANGE = "assignees";
    public static final String RELIEF_NAME = "reliefReq";

    @Bean
    public Queue queue() {
        // enable priority-queue
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 3);

        // durable = true, exclusive & autodelete are fasle
        return new Queue(QUEUE_NAME, true, false, false, args);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("relief.#"); // mapping to "relief.*"
    }

    @Bean
    public Queue queue02() {
        return new Queue(RELIEF_NAME, true, false, false);
    }
    @Bean
    public TopicExchange exchange02() {
        return new TopicExchange(ASSIGNEE_EXCHANGE);
    }
    @Bean
    public Binding binding02(Queue queue02, TopicExchange exchange02) {
        return BindingBuilder.bind(queue02).to(exchange02).with("relief.#"); // mapping to "relief.*"
    }
}
