package com.bootx.util.ali.text2image.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageGenerationRequest implements Serializable {

    /**
     *  指明需要调用的模型，固定值wanx-style-cosplay-v1
     */
    private String model = "wanx-style-cosplay-v1";

    private Input input = new Input();

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Input implements Serializable {

        /**
         * 输入的人脸图像 URL。
         * 说明   可支持输入分辨率范围：不小于256*256，不超过5760*3240, 长宽比不超过1.8:1
         */
        @JsonProperty("face_image_url")
        private String faceImageUrl;

        /**
         * 输入的模板图像 URL。
         * 说明   可支持输入分辨率范围：不小于256*256，不超过5760*3240, 长宽比不超过1.8:1
         */
        @JsonProperty("template_image_url")
        private String templateImageUrl;

        /**
         * 生成风格。取值范围：
         *
         *  1：3D卡通形象（3dcartoon）
         */
        @JsonProperty("model_index")
        private Integer modelIndex;

        public String getFaceImageUrl() {
            return faceImageUrl;
        }

        public void setFaceImageUrl(String faceImageUrl) {
            this.faceImageUrl = faceImageUrl;
        }

        public String getTemplateImageUrl() {
            return templateImageUrl;
        }

        public void setTemplateImageUrl(String templateImageUrl) {
            this.templateImageUrl = templateImageUrl;
        }

        public Integer getModelIndex() {
            return modelIndex;
        }

        public void setModelIndex(Integer modelIndex) {
            this.modelIndex = modelIndex;
        }
    }
}
