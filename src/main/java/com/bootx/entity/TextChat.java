package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Comment;

/**
 * 聊天
 * @author black
 */
@Entity
public class TextChat extends BaseEntity<Long>{

    @Comment("消息编号")
    @Column(nullable = false,updatable = false,unique = true)
    private String sn;

    @Comment("消息内容")
    @Column(nullable = false,updatable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private TextChat next;
}
