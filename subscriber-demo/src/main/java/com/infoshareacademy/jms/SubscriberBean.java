package com.infoshareacademy.jms;

import com.infoshareacademy.data.MessageRepository;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destinationLookup",
                        propertyValue = "java:/jms/topic/ISA.TOPIC"
                ),
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "javax.jms.Topic"
                )
        }
)
public class SubscriberBean implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriberBean.class);

    @Inject
    private MessageRepository messageRepository;

    @Override
    public void onMessage(Message message) {
        LOG.info("Received message: {}", message);

        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String msg = textMessage.getText();
                LOG.info("Received text message: {}", msg);

                messageRepository.addMessage(msg);
            }
        } catch (Exception e) {
            LOG.error("Error occurred: ", e);
        }
    }
}