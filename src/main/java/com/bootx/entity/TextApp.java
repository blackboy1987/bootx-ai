package com.bootx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

/**
 * AI 文本
 */
@Entity
public class TextApp extends OrderedEntity<Long>{

    private String name;

    private String memo;

    private String icon;

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
}
