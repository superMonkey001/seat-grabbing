package cn.hncj.grabbing.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author FanJian
 * @Date 2022/10/6 21:02
 */

@Configuration
public class RabbitMQTopicConfig {
    private static final String QUEUE = "grabQueue";
    private static final String EXCHANGE = "grabExchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("gtab.#");
    }
}
