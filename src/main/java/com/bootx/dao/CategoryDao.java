
package com.bootx.dao;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.entity.Category;

import java.util.List;

/**
 * Dao - 商品分类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CategoryDao extends BaseDao<Category, Long> {

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
	List<Category> findList(Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<Category> findRoots(Integer count);

	/**
	 * 查找上级商品分类
	 * 
	 * @param category
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<Category> findParents(Category category, boolean recursive, Integer count);

	/**
	 * 查找下级商品分类
	 * 
	 * @param category
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<Category> findChildren(Category category, boolean recursive, Integer count);

}