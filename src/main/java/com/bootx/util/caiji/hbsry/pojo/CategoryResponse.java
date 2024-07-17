package com.bootx.util.caiji.hbsry.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResponse {

    private DataBean data = new DataBean();

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataBean {
        private List<HotsBean> hots = new ArrayList<>();
        private List<HotsBean> vip = new ArrayList<>();

        public List<HotsBean> getHots() {
            return hots;
        }

        public void setHots(List<HotsBean> hots) {
            this.hots = hots;
        }

        public List<HotsBean> getVip() {
            return vip;
        }

        public void setVip(List<HotsBean> vip) {
            this.vip = vip;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class HotsBean {
            private String type;
            private String name;
            private String icon;
            private String description;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

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
        }
    }
}
