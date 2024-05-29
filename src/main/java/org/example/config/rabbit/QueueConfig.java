package org.example.config.rabbit;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * @author yuhuofei
 * @version 1.0
 * @description 创建队列、交换机并绑定
 * @date 2022/10/3 18:15
 */
public class QueueConfig {


    @Resource(name = "oneRabbitAdmin")
    private RabbitAdmin oneRabbitAdmin;

    @Resource(name = "twoRabbitAdmin")
    private RabbitAdmin twoRabbitAdmin;

    @Value("${one.out.queue}")
    private String oneOutQueue;

    @Value("${one.out.queue}")
    private String oneRoutingKey;

    @Value("${two.output.queue}")
    private String twoOutQueue;

    @Value("${two.output.queue}")
    private String twoRoutingKey;

    @Value("${one.topic.exchange.name}")
    private String oneTopicExchange;

    @Value("${two.topic.exchange.name}")
    private String twoTopicExchange;

    @PostConstruct
    public void oneRabbitInit() {
        //声明交换机
        oneRabbitAdmin.declareExchange(new TopicExchange(oneTopicExchange, true, false));
        //声明队列
        oneRabbitAdmin.declareQueue(new Queue(oneOutQueue, true, false, false));
        //绑定队列及交换机
        oneRabbitAdmin.declareBinding(BindingBuilder.bind(new Queue(oneOutQueue, true, false, false))
                .to(new TopicExchange(oneTopicExchange, true, false))
                .with(oneRoutingKey));
    }

    @PostConstruct
    public void twoRabbitInit() {
        //声明交换机
        twoRabbitAdmin.declareExchange(new TopicExchange(twoTopicExchange, true, false));
        //声明队列
        twoRabbitAdmin.declareQueue(new Queue(twoOutQueue, true));
        //绑定队列及交换机
        twoRabbitAdmin.declareBinding(BindingBuilder.bind(new Queue(twoOutQueue, true, false, false))
                .to(new TopicExchange(twoTopicExchange, true, false))
                .with(twoRoutingKey));
    }
}