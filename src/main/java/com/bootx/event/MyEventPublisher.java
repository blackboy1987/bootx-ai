package com.bootx.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author black
 */
@Component
public class MyEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public MyEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(Map<String,Object> map) {
        applicationEventPublisher.publishEvent(new MyEvent(this,map));
    }
}