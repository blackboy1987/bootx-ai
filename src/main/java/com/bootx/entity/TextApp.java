package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * AI 文本
 * @author black
 */
@Entity
public class TextApp extends OrderedEntity<Long>{

    @JsonView({PageView.class,ViewView.class,InfoView.class})
    private String name;
    @JsonView({PageView.class,ViewView.class})
    private String memo;
    @JsonView({PageView.class,ViewView.class})
    private String icon;
    @JsonView({PageView.class,ViewView.class,InfoView.class})
    @Column(length = 8000)
    @Convert(converter = FormItemConverter.class)
    private List<FormItem> formList = new ArrayList<>();

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TextAppCategory textAppCategory;

    @JsonView({PageView.class,ViewView.class})
    @Column(length = 2000)
    private String prompt;
    @JsonView({PageView.class,ViewView.class})
    @Column(length = 2000)
    private String userPrompt;

    /**0：创作
     * 1：顾问
     * 2: 办公
     */
    @NotNull
    @Column(nullable = false)
    @JsonView({PageView.class,ViewView.class})
    private Integer type;

    @Comment("是否推荐。true：是，false：否")
    @JsonView({PageView.class,ViewView.class})
    private Boolean isRecommend;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public TextAppCategory getTextAppCategory() {
        return textAppCategory;
    }

    public void setTextAppCategory(TextAppCategory textAppCategory) {
        this.textAppCategory = textAppCategory;
    }

    public List<FormItem> getFormList() {
        return formList;
    }

    public void setFormList(List<FormItem> formList) {
        this.formList = formList;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public interface InfoView extends IdView {
    }

    @Convert
    public static class FormItemConverter extends BaseAttributeConverter<List<FormItem>>{

    }

    @Transient
    @JsonView({PageView.class})
    public String getTextAppCategoryName(){
        return textAppCategory!=null?textAppCategory.getName():null;
    }

    @Transient
    @JsonView({ViewView.class})
    public Long getTextAppCategoryId(){
        return textAppCategory!=null?textAppCategory.getId():null;
    }
}
