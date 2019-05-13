package com.infoshareacademy.activemqdemo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
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

            Destination destination = session.createQueue("NAZWA_KOLEJKI");

            MessageConsumer consumer = session.createConsumer(destination);

            System.out.println("Started ... ");

            // receive message and print it
            // if message = exit, stop the application

            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            System.out.println("Caught: " + e);
        }
    }

    @Override
    public void onException(JMSException e) {
        System.out.println("JMS Exception occurred: " + e);
    }
}
