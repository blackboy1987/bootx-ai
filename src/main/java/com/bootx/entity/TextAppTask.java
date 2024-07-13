package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

/**
 * @author black
 */
@Entity
public class TextAppTask extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    @JsonView({PageView.class})
    private String taskId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private TextApp textApp;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String params;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String prompt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

    /**
     * 0: 待处理
     * 1：处理中
     * 2：已完成
     * 3：已取消
     * 4：处理失败
     */
    @NotNull
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Integer status;

    @Column(columnDefinition = "longtext")
    private String result;

    public @NotEmpty String getTaskId() {
        return taskId;
    }

    public void setTaskId(@NotEmpty String taskId) {
        this.taskId = taskId;
    }

    public TextApp getTextApp() {
        return textApp;
    }

    public void setTextApp(TextApp textApp) {
        this.textApp = textApp;
    }

    public @NotEmpty String getParams() {
        return params;
    }

    public void setParams(@NotEmpty String params) {
        this.params = params;
    }

    public @NotNull Member getMember() {
        return member;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setMember(@NotNull Member member) {
        this.member = member;
    }

    public @NotNull Integer getStatus() {
        return status;
    }

    public void setStatus(@NotNull Integer status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Transient
    @JsonView({PageView.class})
    public String getAppName(){
        if(textApp!=null){
            return textApp.getName();
        }

        return null;
    }


    @Transient
    @JsonView({PageView.class})
    public String getAppLogo(){
        if(textApp!=null){
            return textApp.getIcon();
        }

        return null;
    }
}
