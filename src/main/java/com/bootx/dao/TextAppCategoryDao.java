
package com.bootx.dao;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.entity.TextAppCategory;

import java.util.List;

/**
 * Dao - 分类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface TextAppCategoryDao extends BaseDao<TextAppCategory, Long> {

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
	List<TextAppCategory> findList(Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找顶级分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级分类
	 */
	List<TextAppCategory> findRoots(Integer count);

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
	List<TextAppCategory> findParents(TextAppCategory category, boolean recursive, Integer count);

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
	List<TextAppCategory> findChildren(TextAppCategory category, boolean recursive, Integer count);

}