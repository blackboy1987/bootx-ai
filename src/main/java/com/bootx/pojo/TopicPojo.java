package com.bootx.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicPojo implements Serializable {


    private String title;
    private List<PromptsBean> prompts = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PromptsBean> getPrompts() {
        return prompts;
    }

    public void setPrompts(List<PromptsBean> prompts) {
        this.prompts = prompts;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PromptsBean {

        private String title;
        private String desc;
        private String thumb;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
