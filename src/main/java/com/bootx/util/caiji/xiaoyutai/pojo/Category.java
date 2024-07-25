package com.bootx.util.caiji.xiaoyutai.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
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
        private List<ChildBean> child = new ArrayList<>();

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

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ChildBean {
            private String id;
            private String title;
            private String path;
            private String seoTitle;
            private String seoKey;
            private String seoDesc;
            private List<TemplatesBean> templates = new ArrayList<>();

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

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
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

            public List<TemplatesBean> getTemplates() {
                return templates;
            }

            public void setTemplates(List<TemplatesBean> templates) {
                this.templates = templates;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class TemplatesBean {
                private String id;
                private String title;
                private String icon;
                private String description;

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
            }
        }
    }
}
