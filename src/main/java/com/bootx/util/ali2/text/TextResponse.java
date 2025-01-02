package com.bootx.util.ali2.text;

import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.GenerationUsage;
import com.bootx.util.MessagePojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TextResponse {

    private String content;

    private Boolean isEnd;

    private Integer inputTokens;

    private Integer outputTokens;

    private Integer totalTokens;

    public static TextResponse init(GenerationResult message) {
        TextResponse textResponse = new TextResponse();
        GenerationOutput output = message.getOutput();
        List<GenerationOutput.Choice> choices = output.getChoices();
        GenerationUsage usage = message.getUsage();
        if(choices.isEmpty()){
            textResponse.setIsEnd(true);
            textResponse.setContent("");
        }else{
            textResponse.setContent(choices.getFirst().getMessage().getContent());
            textResponse.setIsEnd(StringUtils.equalsAnyIgnoreCase(choices.getFirst().getFinishReason(),"stop"));
        }
        textResponse.setInputTokens(usage.getInputTokens());
        textResponse.setOutputTokens(usage.getOutputTokens());
        textResponse.setTotalTokens(usage.getTotalTokens());
        return textResponse;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
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
}
