package com.uvytautas.mqcrosswalk.config;

import com.ibm.mq.jms.MQConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {


    @Bean(name = "activemq")
    public JmsComponent activeMqCamelComponent(@Value("${activemq.user}") String username, @Value("${activemq.password}") String password, @Value("${activemq.url}") String brokerUrl) {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUserName(username);
        activeMQConnectionFactory.setPassword(password);
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        return JmsComponent.jmsComponentClientAcknowledge(activeMQConnectionFactory);
    }

    @Bean(name = "ibmmq")
    public JmsComponent ibmMqCamelComponent(MQConnectionFactory mqConnectionFactory) {
        return JmsComponent.jmsComponentClientAcknowledge(mqConnectionFactory);
    }


}
