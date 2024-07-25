package com.bootx.util.caiji.yisixiezuo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private Integer code;
    private Integer show;
    private String msg;
    private List<DataDTO> data = new ArrayList<>();

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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataDTO {
        private Integer id;
        private String name;
        private List<CategoryDTO> category =  new ArrayList<>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CategoryDTO> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryDTO> category) {
            this.category = category;
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CategoryDTO {
            private Integer id;
            private String title;
            private String image;
            private String intro;
            @JsonProperty("outline_status")
            private Integer outlineStatus;
            @JsonProperty("gen_count")
            private Integer genCount;
            @JsonProperty("is_recommend")
            private Integer isRecommend;

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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public Integer getOutlineStatus() {
                return outlineStatus;
            }

            public void setOutlineStatus(Integer outlineStatus) {
                this.outlineStatus = outlineStatus;
            }

            public Integer getGenCount() {
                return genCount;
            }

            public void setGenCount(Integer genCount) {
                this.genCount = genCount;
            }

            public Integer getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(Integer isRecommend) {
                this.isRecommend = isRecommend;
            }
        }
    }
}
