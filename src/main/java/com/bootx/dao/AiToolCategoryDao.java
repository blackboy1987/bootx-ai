package com.bootx.dao;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.entity.AiToolCategory;

import java.util.List;

/**
 * @author black
 */
public interface AiToolCategoryDao extends BaseDao<AiToolCategory,Long> {

    /**
     * 查找分类
     *
     * @param count
     *            数量
     * @param filters
     *            筛选
     * @param orders
     *            排序
     * @return 分类
     */
    List<AiToolCategory> findList(Integer count, List<Filter> filters, List<Order> orders);

    /**
     * 查找顶级分类
     *
     * @param count
     *            数量
     * @return 顶级分类
     */
    List<AiToolCategory> findRoots(Integer count);

    /**
     * 查找上级分类
     *
     * @param category
     *            分类
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 上级分类
     */
    List<AiToolCategory> findParents(AiToolCategory category, boolean recursive, Integer count);

    /**
     * 查找下级分类
     *
     * @param category
     *            分类
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 下级分类
     */
    List<AiToolCategory> findChildren(AiToolCategory category, boolean recursive, Integer count);
}
