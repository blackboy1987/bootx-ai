
package com.bootx.service;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.entity.TextAppCategory;

import java.util.List;

/**
 * Service - 商品分类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface TextAppCategoryService extends BaseService<TextAppCategory, Long> {

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
	List<TextAppCategory> findList(Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找顶级商品分类
	 * 
	 * @return 顶级商品分类
	 */
	List<TextAppCategory> findRoots();

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<TextAppCategory> findRoots(Integer count);

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级商品分类
	 */
	List<TextAppCategory> findRoots(Integer count, boolean useCache);

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
	List<TextAppCategory> findParents(TextAppCategory textAppTextAppCategory, boolean recursive, Integer count);

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
	List<TextAppCategory> findParents(Long textAppTextAppCategoryId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找商品分类树
	 * 
	 * @return 商品分类树
	 */
	List<TextAppCategory> findTree();

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
	List<TextAppCategory> findChildren(TextAppCategory textAppTextAppCategory, boolean recursive, Integer count);

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
	List<TextAppCategory> findChildren(Long textAppTextAppCategoryId, boolean recursive, Integer count, boolean useCache);

	TextAppCategory findByName(String name);
}