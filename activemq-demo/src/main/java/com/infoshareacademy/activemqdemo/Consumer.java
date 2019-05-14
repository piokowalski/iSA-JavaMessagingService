package com.infoshareacademy.activemqdemo;

import javax.jms.*;
import javax.sound.midi.Soundbank;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Runnable, ExceptionListener {

    public static void main(String[] args) {
        new Consumer().run();
    }

    @Override
    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616");

            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("ISA.JJDD6.MSG.QUEUE");

            MessageConsumer consumer = session.createConsumer(destination);

            System.out.println("Started ... ");
            while (true) {
                Message message = consumer.receive();
                if (message instanceof TextMessage) {
                    String msg = ((TextMessage) message).getText();
                    System.out.println("Received: " + msg);

                    if (msg.equals("exit")) {
                        break;
                    }
                }
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            System.out.println("Caught: " + e);
        }
    }

    @Override
    public void onException(JMSException e) {
        System.out.println
                ("JMS Exception occurred: " + e);
    }
}
