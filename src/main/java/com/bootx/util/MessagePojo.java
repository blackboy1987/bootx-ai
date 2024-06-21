package com.bootx.util;

import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;

import java.io.Serializable;

/**
 * @author black
 */
public class MessagePojo implements Serializable {

    private String requestId;

    private Integer inputTokens;

    private Integer outputTokens;

    private Integer totalTokens;

    /**
     * null 没有结束
     * stop 结束
     */
    private String finishReason;

    private String role;

    private String content;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getInputTokens() {
        return inputTokens;
    }

    public void setInputTokens(Integer inputTokens) {
        this.inputTokens = inputTokens;
    }

    public Integer getOutputTokens() {
        return outputTokens;
    }

    public void setOutputTokens(Integer outputTokens) {
        this.outputTokens = outputTokens;
    }

    public Integer getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(Integer totalTokens) {
        this.totalTokens = totalTokens;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void init(GenerationResult message) {
        GenerationOutput output = message.getOutput();
        GenerationOutput.Choice choice = output.getChoices().get(0);
        this.requestId = message.getRequestId();
        this.inputTokens = message.getUsage().getInputTokens();
        this.outputTokens = message.getUsage().getOutputTokens();
        this.totalTokens = message.getUsage().getTotalTokens();
        this.finishReason = choice.getFinishReason();
        this.role = choice.getMessage().getRole();
        this.content = choice.getMessage().getContent();
    }

    public static MessagePojo stop(){
        MessagePojo messagePojo = new MessagePojo();
        messagePojo.setFinishReason("stop");

        return messagePojo;
    }

    public static MessagePojo empty(){
        MessagePojo messagePojo = new MessagePojo();
        messagePojo.setFinishReason("empty");

        return messagePojo;
    }
}
