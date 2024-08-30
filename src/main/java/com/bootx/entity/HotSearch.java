package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Comment;

@Entity
@Comment("热门搜索")
public class HotSearch extends OrderedEntity<Long>{

    @Column(nullable = false,unique = true)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
