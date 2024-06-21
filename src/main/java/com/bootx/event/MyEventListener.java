package com.bootx.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener {
    @EventListener
    public String handleEvent(String message) {
        System.out.println("handleEvent:"+ message);
        return message;
    }
}