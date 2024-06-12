package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

/** 创作分类
 * @author black
 */
@Entity
public class Topic extends OrderedEntity<Long> {

    @JsonView({PageView.class})
    @NotEmpty
    @Column(nullable = false,unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
