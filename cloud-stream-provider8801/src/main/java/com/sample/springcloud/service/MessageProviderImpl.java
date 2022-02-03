package com.sample.springcloud.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import lombok.extern.slf4j.Slf4j;

@EnableBinding(Source.class)
@Slf4j
public class MessageProviderImpl implements MessageProvider {

    @Autowired
    private MessageChannel output;

    public void send() {
        String message = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(message).build());
        log.info("send message: " + message);
    }

}
