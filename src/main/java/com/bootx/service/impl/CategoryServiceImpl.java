
package com.bootx.service.impl;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.dao.CategoryDao;
import com.bootx.entity.Category;
import com.bootx.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * Service - 商品分类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {

	@Resource
	private CategoryDao categoryDao;

	@Override
	@Transactional(readOnly = true)
	public List<Category> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return categoryDao.findList(count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> findRoots() {
		return categoryDao.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> findRoots(Integer count) {
		return categoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "category", condition = "#useCache")
	public List<Category> findRoots(Integer count, boolean useCache) {
		return categoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> findParents(Category category, boolean recursive, Integer count) {
		return categoryDao.findParents(category, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "category", condition = "#useCache")
	public List<Category> findParents(Long categoryId, boolean recursive, Integer count, boolean useCache) {
		Category category = categoryDao.find(categoryId);
		if (categoryId != null && category == null) {
			return Collections.emptyList();
		}
		return categoryDao.findParents(category, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> findTree() {
		return categoryDao.findChildren(null, true, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> findChildren(Category category, boolean recursive, Integer count) {
		return categoryDao.findChildren(category, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "category", condition = "#useCache")
	public List<Category> findChildren(Long categoryId, boolean recursive, Integer count, boolean useCache) {
		Category category = categoryDao.find(categoryId);
		if (categoryId != null && category == null) {
			return Collections.emptyList();
		}
		return categoryDao.findChildren(category, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "category" }, allEntries = true)
	public Category save(Category category) {
		Assert.notNull(category, "[Assertion failed] - category is required; it must not be null");

		setValue(category);
		return super.save(category);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "category" }, allEntries = true)
	public Category update(Category category) {
		Assert.notNull(category, "[Assertion failed] - category is required; it must not be null");

		setValue(category);
		for (Category children : categoryDao.findChildren(category, true, null)) {
			setValue(children);
		}
		return super.update(category);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "category" }, allEntries = true)
	public Category update(Category category, String... ignoreProperties) {
		return super.update(category, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "category" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "category" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "category" }, allEntries = true)
	public void delete(Category category) {
		super.delete(category);
	}

	/**
	 * 设置值
	 * 
	 * @param category
	 *            商品分类
	 */
	private void setValue(Category category) {
		if (category == null) {
			return;
		}
		Category parent = category.getParent();
		if (parent != null) {
			category.setTreePath(parent.getTreePath() + parent.getId() + Category.TREE_PATH_SEPARATOR);
		} else {
			category.setTreePath(Category.TREE_PATH_SEPARATOR);
		}
		category.setGrade(category.getParentIds().length);
	}

}