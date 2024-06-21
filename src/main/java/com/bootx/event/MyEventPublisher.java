package com.bootx.event;

import com.bootx.util.JsonUtils;
import com.bootx.util.MessagePojo;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author black
 */
@Component
public class MyEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public MyEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(MessagePojo messagePojo) {
        System.out.println("publishEvent:"+JsonUtils.toJson(messagePojo));
        applicationEventPublisher.publishEvent(new MyEvent(this, messagePojo));
    }
}