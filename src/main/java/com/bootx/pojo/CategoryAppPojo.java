package com.bootx.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryAppPojo implements Serializable {

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataBean {

        private String name;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("description")
        private String description;
        @JsonProperty("Createbots")
        private List<CreatebotsBean> Createbots = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<CreatebotsBean> getCreatebots() {
            return Createbots;
        }

        public void setCreatebots(List<CreatebotsBean> Createbots) {
            this.Createbots = Createbots;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CreatebotsBean {

            private String name;
            private String profile;
            @JsonProperty("welcome_message")
            private String welcomeMessage;

            private Object formList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProfile() {
                return profile;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }

            public String getWelcomeMessage() {
                return welcomeMessage;
            }

            public void setWelcomeMessage(String welcomeMessage) {
                this.welcomeMessage = welcomeMessage;
            }

            public Object getFormList() {
                return formList;
            }

            public void setFormList(Object formList) {
                this.formList = formList;
            }
        }
    }
}
