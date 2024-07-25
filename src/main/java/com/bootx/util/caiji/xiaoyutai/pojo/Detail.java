package com.bootx.util.caiji.xiaoyutai.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Detail {
    @JsonProperty("data")
    private DataBean data = new DataBean();

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataBean {

        private String id;
        private String title;
        private String icon;
        private String description;
        private String seoTitle;
        private String seoKey;
        private String seoDesc;
        private List<ElementBean> element =new ArrayList<>();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getSeoTitle() {
            return seoTitle;
        }

        public void setSeoTitle(String seoTitle) {
            this.seoTitle = seoTitle;
        }

        public String getSeoKey() {
            return seoKey;
        }

        public void setSeoKey(String seoKey) {
            this.seoKey = seoKey;
        }

        public String getSeoDesc() {
            return seoDesc;
        }

        public void setSeoDesc(String seoDesc) {
            this.seoDesc = seoDesc;
        }

        public List<ElementBean> getElement() {
            return element;
        }

        public void setElement(List<ElementBean> element) {
            this.element = element;
        }


        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ElementBean {

            private String title;
            private String value;
            @JsonProperty("default_value")
            private String defaultValue;
            private String sample;
            @JsonProperty("form_type")
            private String formType;
            @JsonProperty("is_required")
            private Integer isRequired;

            private String help;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getDefaultValue() {
                return defaultValue;
            }

            public void setDefaultValue(String defaultValue) {
                this.defaultValue = defaultValue;
            }

            public String getSample() {
                return sample;
            }

            public void setSample(String sample) {
                this.sample = sample;
            }

            public String getFormType() {
                return formType;
            }

            public void setFormType(String formType) {
                this.formType = formType;
            }

            public Integer getIsRequired() {
                return isRequired;
            }

            public void setIsRequired(Integer isRequired) {
                this.isRequired = isRequired;
            }

            public String getHelp() {
                return help;
            }

            public void setHelp(String help) {
                this.help = help;
            }
        }
    }
}
