package com.bootx.util.caiji.yisixiezuo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Detail {

    private Integer code;
    private Integer show;
    private String msg;
    private DataDTO data = new DataDTO();

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataDTO {
        private Integer id;
        private String title;
        private String banner;
        private String intro;
        private List<FormsDTO> forms = new ArrayList<FormsDTO>();
        @JsonProperty("outline_status")
        private Integer outlineStatus;
        private ModelDTO model = new ModelDTO();
        @JsonProperty("sell_price")
        private SellPriceDTO sellPrice = new SellPriceDTO();
        @JsonProperty("problem")
        private List<ProblemDTO> problem = new ArrayList<>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public List<FormsDTO> getForms() {
            return forms;
        }

        public void setForms(List<FormsDTO> forms) {
            this.forms = forms;
        }

        public Integer getOutlineStatus() {
            return outlineStatus;
        }

        public void setOutlineStatus(Integer outlineStatus) {
            this.outlineStatus = outlineStatus;
        }

        public ModelDTO getModel() {
            return model;
        }

        public void setModel(ModelDTO model) {
            this.model = model;
        }

        public SellPriceDTO getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(SellPriceDTO sellPrice) {
            this.sellPrice = sellPrice;
        }

        public List<ProblemDTO> getProblem() {
            return problem;
        }

        public void setProblem(List<ProblemDTO> problem) {
            this.problem = problem;
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ModelDTO {
            private String std;
            private String pro;

            public String getStd() {
                return std;
            }

            public void setStd(String std) {
                this.std = std;
            }

            public String getPro() {
                return pro;
            }

            public void setPro(String pro) {
                this.pro = pro;
            }
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SellPriceDTO {
            private Integer std;
            private Integer pro;

            public Integer getStd() {
                return std;
            }

            public void setStd(Integer std) {
                this.std = std;
            }

            public Integer getPro() {
                return pro;
            }

            public void setPro(Integer pro) {
                this.pro = pro;
            }
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FormsDTO {
            private String id;
            private String name;
            private PropsDTO props = new PropsDTO();

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public PropsDTO getProps() {
                return props;
            }

            public void setProps(PropsDTO props) {
                this.props = props;
            }
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class PropsDTO {
                private String field;
                private String title;
                private String placeholder;
                private String maxlength;
                private String isEnable;
                private Boolean isRequired;
                private Boolean disabled;
                private List<String> options = new ArrayList<>();

                public String getField() {
                    return field;
                }

                public void setField(String field) {
                    this.field = field;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getPlaceholder() {
                    return placeholder;
                }

                public void setPlaceholder(String placeholder) {
                    this.placeholder = placeholder;
                }

                public String getMaxlength() {
                    return maxlength;
                }

                public void setMaxlength(String maxlength) {
                    this.maxlength = maxlength;
                }

                public String getIsEnable() {
                    return isEnable;
                }

                public void setIsEnable(String isEnable) {
                    this.isEnable = isEnable;
                }

                public Boolean getIsRequired() {
                    return isRequired;
                }

                public void setIsRequired(Boolean isRequired) {
                    this.isRequired = isRequired;
                }

                public Boolean getDisabled() {
                    return disabled;
                }

                public void setDisabled(Boolean disabled) {
                    this.disabled = disabled;
                }

                public List<String> getOptions() {
                    return options;
                }

                public void setOptions(List<String> options) {
                    this.options = options;
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ProblemDTO {
            private Integer id;
            private String q;
            private String a;
            private String createTime;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getQ() {
                return q;
            }

            public void setQ(String q) {
                this.q = q;
            }

            public String getA() {
                return a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
