package com.infoshareacademy.activemqdemo;

import java.io.IOException;
import java.util.Scanner;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

    public static void main(String[] args) throws JMSException, IOException {
        ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session =
            connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("ISA.JJDD6.MSG.QUEUE");

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        Scanner scanner = new Scanner(System.in);

        // read the message from the user
        while (true) {
            String message = scanner.nextLine();

            Message jmsMessage = session.createTextMessage(message);
            producer.send(jmsMessage);

            if (message.equals("exit")) {
                break;
            }
        }
        // send it
        // when the message = exit, send it to the queue and stop the application

        session.close();
        connection.close();
    }
}
