package com.bootx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @author black
 */
@Entity
public class CategoryAppTaskResult extends BaseEntity<Long> {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private CategoryAppTask categoryAppTask;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private CategoryApp categoryApp;

    @Lob
    @Column(columnDefinition = "longtext")
    private String content;

    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Long inputTokens;
    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Long outputTokens;
    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Long totalTokens;

    public @NotNull CategoryAppTask getCategoryAppTask() {
        return categoryAppTask;
    }

    public void setCategoryAppTask(@NotNull CategoryAppTask categoryAppTask) {
        this.categoryAppTask = categoryAppTask;
    }

    public @NotNull Member getMember() {
        return member;
    }

    public void setMember(@NotNull Member member) {
        this.member = member;
    }

    public @NotNull CategoryApp getCategoryApp() {
        return categoryApp;
    }

    public void setCategoryApp(@NotNull CategoryApp categoryApp) {
        this.categoryApp = categoryApp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public @NotNull @Min(0) Long getInputTokens() {
        return inputTokens;
    }

    public void setInputTokens(@NotNull @Min(0) Long inputTokens) {
        this.inputTokens = inputTokens;
    }

    public @NotNull @Min(0) Long getOutputTokens() {
        return outputTokens;
    }

    public void setOutputTokens(@NotNull @Min(0) Long outputTokens) {
        this.outputTokens = outputTokens;
    }

    public @NotNull @Min(0) Long getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(@NotNull @Min(0) Long totalTokens) {
        this.totalTokens = totalTokens;
    }
}
