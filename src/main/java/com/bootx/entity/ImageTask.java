package com.bootx.entity;

import jakarta.persistence.*;

/**
 * Entity-ImageTask
 *
 * @author 一枚猿：blackboyhjy1987
 */
@Entity
public class ImageTask extends BaseEntity<Long>{

    @Column(nullable = false,updatable = false)
    private String requestId;

    @Column(nullable = false,updatable = false)
    private String taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private Member member;

    @Column(length = 4000)
    private String result;

    private Integer status;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
