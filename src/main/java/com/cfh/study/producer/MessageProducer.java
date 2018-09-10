package com.cfh.study.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @Author: cfh
 * @Date: 2018/9/10 20:14
 * @Description: 消息生产者
 */
public class MessageProducer {
    private static final String EXCHANGE = "fanoutExchange";

    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");//设置rabbitmq服务的ip
        //connectionFactory.setPort();//如果不设置则使用默认端口
        Connection connection = connectionFactory.newConnection();//通过connectionFactory打开一个连接

        Channel channel = connection.createChannel();//获取一个通信管道
        channel.exchangeDeclare(EXCHANGE,"fanout");//声明一个exchange

        //消息中带的点号数目说明处理该消息所要消费的时间
        String[] messages = new String[]{"First message.", "Second message..",
                "Third message...", "Fourth message....", "Fifth message....."};
        for (String message : messages) {
            //设置channel的ExCHANGE类型，注意这里没有使用自己指定的队列
            channel.basicPublish(EXCHANGE, "", MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("[x] Sent '" + message + "'");
        }

        channel.close();;
        connection.close();
    }
}
