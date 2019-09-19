package com.uvytautas.mqcrosswalk.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMqConfiguration {

    @Value("${activemq.url}")
    private String brokerUrl;
    @Value("${activemq.user}")
    private String username;
    @Value("${activemq.password}")
    private String password;

    @Bean
    public ActiveMQComponent activemq() {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUserName(username);
        activeMQConnectionFactory.setPassword(password);
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQComponent.setConnectionFactory(activeMQConnectionFactory);
        return activeMQComponent;
    }


}
