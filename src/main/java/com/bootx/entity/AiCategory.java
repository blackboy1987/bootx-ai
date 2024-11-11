package com.bootx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * AI文本分类
 */
@Entity
public class AiCategory extends OrderedEntity<Long>{
    /**
     * 树路径分隔符
     */
    public static final String TREE_PATH_SEPARATOR = ",";
    
    @NotEmpty
    @Column(nullable = false,unique = true)
    private String name;

    /**
     * 树路径
     */
    @Column(nullable = false)
    private String treePath;

    /**
     * 层级
     */
    @Column(nullable = false)
    private Integer grade;

    @ManyToOne(fetch = FetchType.LAZY)
    private AiCategory parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AiCategory getParent() {
        return parent;
    }

    public void setParent(AiCategory parent) {
        this.parent = parent;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取所有上级分类ID
     *
     * @return 所有上级分类ID
     */
    @Transient
    public Long[] getParentIds() {
        String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
        Long[] result = new Long[parentIds.length];
        for (int i = 0; i < parentIds.length; i++) {
            result[i] = Long.valueOf(parentIds[i]);
        }
        return result;
    }

    /**
     * 获取所有上级分类
     *
     * @return 所有上级分类
     */
    @Transient
    public List<AiCategory> getParents() {
        List<AiCategory> parents = new ArrayList<>();
        recursiveParents(parents, this);
        return parents;
    }

    /**
     * 递归上级分类
     *
     * @param parents
     *            上级分类
     * @param textAppCategory
     *            商品分类
     */
    private void recursiveParents(List<AiCategory> parents, AiCategory textAppCategory) {
        if (textAppCategory == null) {
            return;
        }
        AiCategory parent = textAppCategory.getParent();
        if (parent != null) {
            parents.add(0, parent);
            recursiveParents(parents, parent);
        }
    }
}
