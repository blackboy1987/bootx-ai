package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Comment;

/**
 * 聊天室
 * @author black
 */
@Entity
public class TextChatRoom extends BaseEntity<Long>{

    @Comment("聊天室编号")
    @Column(nullable = false,updatable = false,unique = true)
    private String sn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
