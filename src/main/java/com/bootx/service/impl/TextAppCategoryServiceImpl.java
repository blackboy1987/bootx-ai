
package com.bootx.service.impl;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.dao.TextAppCategoryDao;
import com.bootx.entity.TextAppCategory;
import com.bootx.service.TextAppCategoryService;
import jakarta.annotation.Resource;
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
public class TextAppCategoryServiceImpl extends BaseServiceImpl<TextAppCategory, Long> implements TextAppCategoryService {

	@Resource
	private TextAppCategoryDao textAppCategoryDao;

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return textAppCategoryDao.findList(count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findRoots() {
		return textAppCategoryDao.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findRoots(Integer count) {
		return textAppCategoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findRoots(Integer count, boolean useCache) {
		return textAppCategoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findParents(TextAppCategory textAppCategory, boolean recursive, Integer count) {
		return textAppCategoryDao.findParents(textAppCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findParents(Long textAppCategoryId, boolean recursive, Integer count, boolean useCache) {
		TextAppCategory textAppCategory = textAppCategoryDao.find(textAppCategoryId);
		if (textAppCategoryId != null && textAppCategory == null) {
			return Collections.emptyList();
		}
		return textAppCategoryDao.findParents(textAppCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findTree() {
		return textAppCategoryDao.findChildren(null, true, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findChildren(TextAppCategory textAppCategory, boolean recursive, Integer count) {
		return textAppCategoryDao.findChildren(textAppCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TextAppCategory> findChildren(Long textAppCategoryId, boolean recursive, Integer count, boolean useCache) {
		TextAppCategory textAppCategory = textAppCategoryDao.find(textAppCategoryId);
		if (textAppCategoryId != null && textAppCategory == null) {
			return Collections.emptyList();
		}
		return textAppCategoryDao.findChildren(textAppCategory, recursive, count);
	}

	@Override
	public TextAppCategory findByName(String name) {
		return textAppCategoryDao.find("name",name);
	}

	@Override
	@Transactional
	public TextAppCategory save(TextAppCategory textAppCategory) {
		Assert.notNull(textAppCategory, "[Assertion failed] - textAppCategory is required; it must not be null");
		setValue(textAppCategory);
		return super.save(textAppCategory);
	}

	@Override
	@Transactional
	public TextAppCategory update(TextAppCategory textAppCategory) {
		Assert.notNull(textAppCategory, "[Assertion failed] - textAppCategory is required; it must not be null");

		setValue(textAppCategory);
		for (TextAppCategory children : textAppCategoryDao.findChildren(textAppCategory, true, null)) {
			setValue(children);
		}
		return super.update(textAppCategory);
	}

	@Override
	@Transactional
	public TextAppCategory update(TextAppCategory textAppCategory, String... ignoreProperties) {
		return super.update(textAppCategory, ignoreProperties);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	public void delete(TextAppCategory textAppCategory) {
		super.delete(textAppCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param textAppCategory
	 *            商品分类
	 */
	private void setValue(TextAppCategory textAppCategory) {
		if (textAppCategory == null) {
			return;
		}
		TextAppCategory parent = textAppCategory.getParent();
		if (parent != null) {
			textAppCategory.setTreePath(parent.getTreePath() + parent.getId() + TextAppCategory.TREE_PATH_SEPARATOR);
		} else {
			textAppCategory.setTreePath(TextAppCategory.TREE_PATH_SEPARATOR);
		}
		textAppCategory.setGrade(textAppCategory.getParentIds().length);
	}

}