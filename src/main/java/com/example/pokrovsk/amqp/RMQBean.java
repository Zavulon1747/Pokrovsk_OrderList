package com.example.pokrovsk.amqp;

import com.example.pokrovsk.entity.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RMQBean {

    RabbitTemplate rabbitTemplate;

    public RMQBean(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "{product.amqp.queue}", containerFactory = "amqpFactory")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");

    }

    public void sendTo(@Value("${product.amqp.queue}") String queueAddress, Product product) {
        rabbitTemplate.convertAndSend(queueAddress, product);
    }




}
