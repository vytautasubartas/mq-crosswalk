package com.uvytautas.mqcrosswalk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ibmMq")
public class IbmMqProducerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IbmMqProducerController.class);
    private final JmsTemplate jmsTemplate;

    public IbmMqProducerController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("send")
    public void send(@RequestBody String request, @Value("${ibm.mq.destination.queue}") String destinationQueue) {
        try {
            jmsTemplate.convertAndSend(destinationQueue, request);
        } catch (JmsException ex) {
            LOGGER.error("Can't post to Ibm mq", ex);
        }
    }

}
