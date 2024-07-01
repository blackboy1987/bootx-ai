package com.bootx.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author black
 */
@Component
public class MyEventListener {
    @EventListener
    public void handleEvent(MyEvent myEvent) {
        System.out.println("handleEvent:"+ myEvent.getData());
    }
}