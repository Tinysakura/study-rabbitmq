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

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.print(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }

            }
        };
        int precenceCount = 1;
        channel.basicQos(precenceCount);//在消费者返回对一条消息的确认前不再对该消费者分发消息
        boolean autoAck = true; //打开消息确认机制(在消费方发回消息处理完毕的消息后rabbitmq才将消息从消息队列移除)
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);//根据消息中的信息模拟耗时操作
        }
    }
}
