package com.cfh.study.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: cfh
 * @Date: 2018/9/10 20:14
 * @Description: 消息生产者
 */
public class MessageProducer {
    private static final String QUEUE_NAME = "firstQueue";

    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");//设置rabbitmq服务的ip
        //connectionFactory.setPort();//如果不设置则使用默认端口
        Connection connection = connectionFactory.newConnection();//通过connectionFactory打开一个连接

        Channel channel = connection.createChannel();//获取一个通信管道
        channel.queueDeclare(QUEUE_NAME,false, false, false, null);

        String message = "my first rabbitmq message";

        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());//发布消息
        System.out.println("send messages");

        channel.close();;
        connection.close();
    }
}
