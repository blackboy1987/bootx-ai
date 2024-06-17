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
public class CategoryAppTask extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    @JsonView({PageView.class})
    private String taskId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private CategoryApp categoryApp;

    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String params;

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

    private Date taskBeginDate;

    @JsonView({PageView.class})
    private Date taskEndDate;

    public @NotEmpty String getTaskId() {
        return taskId;
    }

    public void setTaskId(@NotEmpty String taskId) {
        this.taskId = taskId;
    }

    public @NotNull CategoryApp getCategoryApp() {
        return categoryApp;
    }

    public void setCategoryApp(@NotNull CategoryApp categoryApp) {
        this.categoryApp = categoryApp;
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

    public void setMember(@NotNull Member member) {
        this.member = member;
    }

    public @NotNull Integer getStatus() {
        return status;
    }

    public void setStatus(@NotNull Integer status) {
        this.status = status;
    }

    public Date getTaskBeginDate() {
        return taskBeginDate;
    }

    public void setTaskBeginDate(Date taskBeginDate) {
        this.taskBeginDate = taskBeginDate;
    }

    public Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    @Transient
    @JsonView({PageView.class})
    public String getAppName(){
        if(categoryApp!=null){
            return categoryApp.getTitle();
        }

        return null;
    }


    @Transient
    @JsonView({PageView.class})
    public String getAppLogo(){
        if(categoryApp!=null){
            return categoryApp.getThumb();
        }

        return null;
    }
}
