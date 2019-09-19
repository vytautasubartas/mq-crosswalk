package com.uvytautas.mqcrosswalk.config;

import com.ibm.mq.jms.MQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqCrosswalkConfiguration {


    @Bean
    public JmsComponent ibmmq(MQConnectionFactory mqConnectionFactory) {
        JmsComponent ibmmq = new JmsComponent();
        ibmmq.setConnectionFactory(mqConnectionFactory);
        return ibmmq;
    }
}
