package com.bootx.dao;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.entity.AiCategory;
import com.bootx.entity.AiCategory;

import java.util.List;

/**
 * @author black
 */
public interface AiCategoryDao extends BaseDao<AiCategory,Long> {

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
    List<AiCategory> findList(Integer count, List<Filter> filters, List<Order> orders);

    /**
     * 查找顶级分类
     *
     * @param count
     *            数量
     * @return 顶级分类
     */
    List<AiCategory> findRoots(Integer count);

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
    List<AiCategory> findParents(AiCategory category, boolean recursive, Integer count);

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
    List<AiCategory> findChildren(AiCategory category, boolean recursive, Integer count);
}
