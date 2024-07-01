package com.bootx.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author black
 */
public class MyEvent extends ApplicationEvent {

    private Map<String,Object> data;

    public MyEvent(Object source,Map<String,Object> data) {
        super(source);
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}