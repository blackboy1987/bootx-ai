
package com.bootx.service;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.entity.AiToolCategory;

import java.util.List;

/**
 * Service - 商品分类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AiToolCategoryService extends BaseService<AiToolCategory, Long> {

    AiToolCategory create(String categoryName,String parentName);

    /**
     * 查找商品分类
     *
     * @param count
     *            数量
     * @param filters
     *            筛选
     * @param orders
     *            排序
     * @return 商品分类
     */
    List<AiToolCategory> findList(Integer count, List<Filter> filters, List<Order> orders);

    /**
     * 查找顶级商品分类
     *
     * @return 顶级商品分类
     */
    List<AiToolCategory> findRoots();

    /**
     * 查找顶级商品分类
     *
     * @param count
     *            数量
     * @return 顶级商品分类
     */
    List<AiToolCategory> findRoots(Integer count);

    /**
     * 查找顶级商品分类
     *
     * @param count
     *            数量
     * @param useCache
     *            是否使用缓存
     * @return 顶级商品分类
     */
    List<AiToolCategory> findRoots(Integer count, boolean useCache);

    /**
     * 查找上级商品分类
     *
     * @param textAppTextAppCategory
     *            商品分类
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 上级商品分类
     */
    List<AiToolCategory> findParents(AiToolCategory textAppTextAppCategory, boolean recursive, Integer count);

    /**
     * 查找上级商品分类
     *
     * @param textAppTextAppCategoryId
     *            商品分类ID
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @param useCache
     *            是否使用缓存
     * @return 上级商品分类
     */
    List<AiToolCategory> findParents(Long textAppTextAppCategoryId, boolean recursive, Integer count, boolean useCache);

    /**
     * 查找商品分类树
     *
     * @return 商品分类树
     */
    List<AiToolCategory> findTree();

    /**
     * 查找下级商品分类
     *
     * @param textAppTextAppCategory
     *            商品分类
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 下级商品分类
     */
    List<AiToolCategory> findChildren(AiToolCategory textAppTextAppCategory, boolean recursive, Integer count);

    /**
     * 查找下级商品分类
     *
     * @param textAppTextAppCategoryId
     *            商品分类ID
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @param useCache
     *            是否使用缓存
     * @return 下级商品分类
     */
    List<AiToolCategory> findChildren(Long textAppTextAppCategoryId, boolean recursive, Integer count, boolean useCache);

    AiToolCategory findByName(String name);


    AiToolCategory create1(AiToolCategory item);
}