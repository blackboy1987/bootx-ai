package com.bootx.event;

import com.bootx.util.MessagePojo;
import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {
    private MessagePojo messagePojo;

    public MyEvent(Object source, MessagePojo messagePojo) {
        super(source);
        this.messagePojo = messagePojo;
    }
    public MessagePojo getMessagePojo() {
        return messagePojo;
    }

    public void setMessagePojo(MessagePojo messagePojo) {
        this.messagePojo = messagePojo;
    }

}