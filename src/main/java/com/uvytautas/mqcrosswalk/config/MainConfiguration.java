package com.uvytautas.mqcrosswalk.config;

import com.ibm.mq.jms.MQConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.JMSException;

import static com.ibm.msg.client.wmq.common.CommonConstants.*;

@Configuration
public class MainConfiguration {

    @Bean
    public ActiveMQConnectionFactory activeMqConnectionFactory(@Value("${activemq.user}") String username, @Value("${activemq.password}") String password, @Value("${activemq.url}") String brokerUrl) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUserName(username);
        activeMQConnectionFactory.setPassword(password);
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }


    @Bean(name = "activemq")
    public JmsComponent activeMqCamelComponent(ActiveMQConnectionFactory activeMqConnectionFactory) {
        return JmsComponent.jmsComponentAutoAcknowledge(activeMqConnectionFactory);
    }

    @Bean
    public MQConnectionFactory mqConnectionFactory(@Value("${ibmmq.user}") String username, @Value("${ibmmq.password}") String password, @Value("${ibmmq.queue-manager}") String queueManager, @Value("${ibmmq.channel}") String channel, @Value("${ibmmq.host}") String host, @Value("${ibmmq.port}") Integer port) throws JMSException {
        MQConnectionFactory mqConnectionFactory = new MQConnectionFactory();

        mqConnectionFactory.setStringProperty(WMQ_HOST_NAME, host);
        mqConnectionFactory.setIntProperty(WMQ_PORT, port);
        mqConnectionFactory.setStringProperty(WMQ_CHANNEL, channel);
        mqConnectionFactory.setIntProperty(WMQ_CONNECTION_MODE, WMQ_CM_CLIENT);
        mqConnectionFactory.setStringProperty(WMQ_QUEUE_MANAGER, queueManager);
        mqConnectionFactory.setStringProperty(USERID, username);
        mqConnectionFactory.setStringProperty(PASSWORD, password);
        mqConnectionFactory.setBooleanProperty(USER_AUTHENTICATION_MQCSP, true);


        return mqConnectionFactory;
    }

    @Bean(name = "ibmmq")
    public JmsComponent ibmMqCamelComponent(MQConnectionFactory mqConnectionFactory) {
        return JmsComponent.jmsComponentAutoAcknowledge(mqConnectionFactory);
    }


}
