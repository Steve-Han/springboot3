package org.example.config.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class RabbitMQConfig {

    //@Value("${c.rabbitmq.addresses}")
    private String host = "192.168.249.128";
    //@Value("${u.rabbitmq.username}")
    private String username = "admin";
    //@Value("${u.rabbitmq.password}")
    private String password = "admin";
    //@Value("${u.rabbitmq.vhost.provider}")
    private int port = 5672;
    private String virtualHost = "/";

    /**
     * 定义与one的连接工厂
     */
    @Bean(name = "oneConnectionFactory")
    @Primary
    public ConnectionFactory oneConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherReturns(true);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    @Bean(name = "oneRabbitTemplate")
    @Primary
    public RabbitTemplate oneRabbitTemplate(@Qualifier("oneConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate oneRabbitTemplate = new RabbitTemplate(connectionFactory);
        oneRabbitTemplate.setMandatory(true);
        oneRabbitTemplate.setConnectionFactory(connectionFactory);
        oneRabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("确认消息送到交换机(Exchange)结果：");
                log.info("相关数据：{}", correlationData);
                boolean ret = false;
                if (ack) {
                    log.info("消息发送到交换机成功, 消息 = {}", correlationData.getId());
                    //下面可自定义业务逻辑处理，如入库保存信息等

                } else {
                    log.error("消息发送到交换机失败! 消息: {}}; 错误原因:cause: {}", correlationData.getId(), cause);
                    //下面可自定义业务逻辑处理，如入库保存信息等

                }
            }
        });

        oneRabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                //获取消息id
                String messageId = returned.getMessage().getMessageProperties().getMessageId();
                // 内容
                String result = null;
                try {
                    result = new String(returned.getMessage().getBody(), "UTF-8");
                } catch (Exception e) {
                    log.error("消息发送失败{}", e);
                }
                log.error("消息发送失败, 消息ID = {}; 消息内容 = {}", messageId, result);
                //下面可自定义业务逻辑处理，如入库保存信息等
            }
        });

        return oneRabbitTemplate;
    }

    @Bean(name = "oneFactory")
    @Primary
    public SimpleRabbitListenerContainerFactory oneFactory(@Qualifier("oneConnectionFactory") ConnectionFactory connectionFactory,
                                                           SimpleRabbitListenerContainerFactoryConfigurer configurer) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name = "oneRabbitAdmin")
    @Primary
    public RabbitAdmin oneRabbitAdmin(@Qualifier("oneConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);

       // String exchange = "ex.crm.takelook";
        //String routingKey = "qu.scm.house_showing_shanghai.ams";

        //声明交换机
      /*  rabbitAdmin.declareExchange(new TopicExchange(exchange, true, false));
        //声明队列
        rabbitAdmin.declareQueue(new Queue(routingKey, true, false, false));
        //绑定队列及交换机
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue(routingKey, true, false, false))
                .to(new TopicExchange(exchange, true, false))
                .with(routingKey));*/

        return rabbitAdmin;
    }

}


