package com.bootx.util.ali.text2image.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageSynthesisRequest implements Serializable {

    /**
     *  指明需要调用的模型，固定值wanx-v1
     */
    private String model = "wanx-v1";


    private Input input = new Input();

    private Parameter parameters = new Parameter();

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

    public Parameter getParameters() {
        return parameters;
    }

    public void setParameters(Parameter parameters) {
        this.parameters = parameters;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Input implements Serializable{

        /**
         *  描述画面的提示词信息。支持中英文，长度不超过500个字符，超过部分会自动截断。
         */
        private String prompt;

        /**
         *  画面中不想出现的内容描述词信息。支持中英文，长度不超过500个字符，超过部分会自动截断。
         */
        @JsonProperty("negative_prompt")
        private String negativePrompt;

        /**
         *  输入参考图像的URL；图片格式可为 jpg，png，tiff，webp等常见位图格式。默认为空。
         */
        @JsonProperty("ref_img")
        private String refImg;

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getNegativePrompt() {
            return negativePrompt;
        }

        public void setNegativePrompt(String negativePrompt) {
            this.negativePrompt = negativePrompt;
        }

        public String getRefImg() {
            return refImg;
        }

        public void setRefImg(String refImg) {
            this.refImg = refImg;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameter implements Serializable{

        /**
         *输出图像的风格，目前支持以下风格取值：
         *
         * "<photography>" 摄影,
         *
         * "<portrait>" 人像写真,
         *
         * "<3d cartoon>" 3D卡通,
         *
         * "<anime>" 动画,
         *
         * "<oil painting>" 油画,
         *
         * "<watercolor>"水彩,
         *
         * "<sketch>" 素描,
         *
         * "<chinese painting>" 中国画,
         *
         * "<flat illustration>" 扁平插画,
         *
         * "<auto>" 默认
         */
        private String style="<auto>";

        /**
         *  生成图像的分辨率，目前仅支持'1024*1024'，'720*1280'，'1280*720'三种分辨率，默认为1024*1024像素。
         */
        private String size="1024*1024";

        /**
         *  本次请求生成的图片数量，目前支持1~4张，默认为1。
         */
        private Integer n=1;

        /**
         *  图片生成时候的种子值，取值范围为(0, 4294967290) 。
         *  如果不提供，则算法自动用一个随机生成的数字作为种子，
         *  如果给定了，则根据 batch 数量分别生成 seed，seed+1，seed+2，seed+3为参数的图片。
         */
        private Integer seed;

        /**
         *  期望输出结果与垫图（参考图）的相似度，取值范围[0.0, 1.0]，数字越大，生成的结果与参考图越相似
         */
        @JsonProperty("ref_strength")
        private String refStrength;

        /**
         *  垫图（参考图）生图使用的生成方式，可选值为'repaint' （默认） 和 'refonly'; 其中 repaint代表参考内容，refonly代表参考风格
         */
        @JsonProperty("ref_mode")
        private String refMode="repaint";

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Integer getN() {
            return n;
        }

        public void setN(Integer n) {
            this.n = n;
        }

        public Integer getSeed() {
            return seed;
        }

        public void setSeed(Integer seed) {
            this.seed = seed;
        }

        public String getRefStrength() {
            return refStrength;
        }

        public void setRefStrength(String refStrength) {
            this.refStrength = refStrength;
        }

        public String getRefMode() {
            return refMode;
        }

        public void setRefMode(String refMode) {
            this.refMode = refMode;
        }
    }
}
