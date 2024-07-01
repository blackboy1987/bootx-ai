package com.bootx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * AI 文本
 */
@Entity
public class TextApp extends OrderedEntity<Long>{

    private String name;

    private String memo;

    private String icon;

    @Column(length = 4000)
    private String formList;

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

    public String getFormList() {
        return formList;
    }

    public void setFormList(String formList) {
        this.formList = formList;
    }
}
