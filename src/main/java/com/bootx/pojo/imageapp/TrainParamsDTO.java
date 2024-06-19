package com.bootx.pojo.imageapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainParamsDTO {
    private String promptPrefix;

    public String getPromptPrefix() {
        return promptPrefix;
    }

    public void setPromptPrefix(String promptPrefix) {
        this.promptPrefix = promptPrefix;
    }
}