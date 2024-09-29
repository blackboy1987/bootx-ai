package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class AiTool extends BaseEntity<Long>{

    @JsonView({ViewView.class})
    private String name;

    @Column(columnDefinition = "varchar(2000)")
    private String icon;

    @JsonView({ViewView.class})
    private String cover;

    @JsonView({ViewView.class})
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    private AiToolCategory aiToolCategory;

    @JsonView({ViewView.class})
    private String url;

    @Column(columnDefinition = "longtext")
    @JsonView({ViewView.class})
    private String description;

    @JsonView({ViewView.class})
    private Long likeCount;

    @JsonView({ViewView.class})
    private Long viewCount;

    private String typeId;

    private String type;

    @Column(nullable = false,updatable = false,unique = true)
    private String otherUrl;

    @JsonView({ViewView.class})
    private String tag;

    @Transient
    private List<String> categoryNames;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public AiToolCategory getAiToolCategory() {
        return aiToolCategory;
    }

    public void setAiToolCategory(AiToolCategory aiToolCategory) {
        this.aiToolCategory = aiToolCategory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
    }

    public String getType() {
        return type;
    }

    public String getOtherUrl() {
        return otherUrl;
    }

    @Transient
    @JsonView({ViewView.class})
    public String getCategory(){
        return aiToolCategory==null?"":aiToolCategory.getName();
    }
}
