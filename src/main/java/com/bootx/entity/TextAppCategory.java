package com.bootx.entity;

import com.alibaba.dashscope.threads.ContentText;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * AI文本分类
 */
@Entity
public class TextAppCategory extends OrderedEntity<Long>{
    /**
     * 树路径分隔符
     */
    public static final String TREE_PATH_SEPARATOR = ",";
    
    @NotEmpty
    @Column(nullable = false,unique = true)
    private String name;

    private String memo;

    private String icon;

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
    private TextAppCategory parent;


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

    public TextAppCategory getParent() {
        return parent;
    }

    public void setParent(TextAppCategory parent) {
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
    public List<TextAppCategory> getParents() {
        List<TextAppCategory> parents = new ArrayList<>();
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
    private void recursiveParents(List<TextAppCategory> parents, TextAppCategory textAppCategory) {
        if (textAppCategory == null) {
            return;
        }
        TextAppCategory parent = textAppCategory.getParent();
        if (parent != null) {
            parents.add(0, parent);
            recursiveParents(parents, parent);
        }
    }
}
