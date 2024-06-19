package com.bootx.pojo.imageapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelListDTO {
    private String modelId;
    private String modelName;
    private String description;
    private String cover;
    private String category;
    private String source;
    private String scope;
    private String platform;
    private TrainParamsDTO trainParams = new TrainParamsDTO();
    private String trainModel;
    private String trainType;
    private List<String> recommendPrompt = new ArrayList<>();

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public TrainParamsDTO getTrainParams() {
        return trainParams;
    }

    public void setTrainParams(TrainParamsDTO trainParams) {
        this.trainParams = trainParams;
    }

    public String getTrainModel() {
        return trainModel;
    }

    public void setTrainModel(String trainModel) {
        this.trainModel = trainModel;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public List<String> getRecommendPrompt() {
        return recommendPrompt;
    }

    public void setRecommendPrompt(List<String> recommendPrompt) {
        this.recommendPrompt = recommendPrompt;
    }
}