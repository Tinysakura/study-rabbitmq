package com.cfh.study.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author: cfh
 * @Date: 2018/9/10 20:39
 * @Description: 消息消费者
 */
public class MessageConsumer {
    private static final String QUEUE_NAME = "firstQueue";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String rec = new String(body);
                System.out.println(rec);
            }
        };//创建消息的消费逻辑

        channel.basicConsume(QUEUE_NAME,true,consumer);//消费者订阅消息队列
    }
}
