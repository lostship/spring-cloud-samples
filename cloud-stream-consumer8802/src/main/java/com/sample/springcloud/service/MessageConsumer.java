package com.sample.springcloud.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

import lombok.extern.slf4j.Slf4j;

@EnableBinding(Sink.class)
@Slf4j
public class MessageConsumer {

    @StreamListener(Sink.INPUT)
    public void consume(Message<String> message) {
        log.info("receive message: " + message.getPayload());
    }

}
