package com.bootx.pojo.imageapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelListDTO {
    private String modelName;
    private String description;
    private String cover;
    private List<String> recommendPrompt = new ArrayList<>();

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<String> getRecommendPrompt() {
        return recommendPrompt;
    }

    public void setRecommendPrompt(List<String> recommendPrompt) {
        this.recommendPrompt = recommendPrompt;
    }
}