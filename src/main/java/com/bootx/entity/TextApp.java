package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * AI 文本
 */
@Entity
public class TextApp extends OrderedEntity<Long>{

    @JsonView({PageView.class})
    private String name;
    @JsonView({PageView.class})
    private String memo;
    @JsonView({PageView.class})
    private String icon;
    @JsonView({PageView.class})
    @Column(length = 4000)
    @Convert(converter = FormItemConverter.class)
    private List<FormItem> formList = new ArrayList<>();

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TextAppCategory textAppCategory;

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

    @Convert
    public static class FormItemConverter extends BaseAttributeConverter<List<FormItem>>{

    }
}
