package com.bootx.event;

import com.bootx.util.JsonUtils;
import com.bootx.util.MessagePojo;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener {
    @EventListener
    public MessagePojo handleEvent(MyEvent event) {
        System.out.println("handleEvent:"+ JsonUtils.toJson(event.getMessagePojo()));
        return event.getMessagePojo();
    }
}