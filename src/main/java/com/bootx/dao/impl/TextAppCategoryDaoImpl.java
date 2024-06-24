
package com.bootx.dao.impl;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.dao.TextAppCategoryDao;
import com.bootx.entity.TextAppCategory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Dao - 分类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class TextAppCategoryDaoImpl extends BaseDaoImpl<TextAppCategory, Long> implements TextAppCategoryDao {

	@Override
	public List<TextAppCategory> findList(Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TextAppCategory> criteriaQuery = criteriaBuilder.createQuery(TextAppCategory.class);
		Root<TextAppCategory> root = criteriaQuery.from(TextAppCategory.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}

	@Override
	public List<TextAppCategory> findRoots(Integer count) {
		String jpql = "select textAppCategory from TextAppCategory textAppCategory where textAppCategory.parent is null order by textAppCategory.order asc";
		TypedQuery<TextAppCategory> query = entityManager.createQuery(jpql, TextAppCategory.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<TextAppCategory> findParents(TextAppCategory textAppCategory, boolean recursive, Integer count) {
		if (textAppCategory == null || textAppCategory.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<TextAppCategory> query;
		if (recursive) {
			String jpql = "select textAppCategory from TextAppCategory textAppCategory where textAppCategory.id in (:ids) order by textAppCategory.grade asc";
			query = entityManager.createQuery(jpql, TextAppCategory.class).setParameter("ids", Arrays.asList(textAppCategory.getParentIds()));
		} else {
			String jpql = "select textAppCategory from TextAppCategory textAppCategory where textAppCategory = :textAppCategory";
			query = entityManager.createQuery(jpql, TextAppCategory.class).setParameter("textAppCategory", textAppCategory.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<TextAppCategory> findChildren(TextAppCategory textAppCategory, boolean recursive, Integer count) {
		TypedQuery<TextAppCategory> query;
		if (recursive) {
			if (textAppCategory != null) {
				String jpql = "select textAppCategory from TextAppCategory textAppCategory where textAppCategory.treePath like :treePath order by textAppCategory.grade asc, textAppCategory.order asc";
				query = entityManager.createQuery(jpql, TextAppCategory.class).setParameter("treePath", "%" + TextAppCategory.TREE_PATH_SEPARATOR + textAppCategory.getId() + TextAppCategory.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select textAppCategory from TextAppCategory textAppCategory order by textAppCategory.grade asc, textAppCategory.order asc";
				query = entityManager.createQuery(jpql, TextAppCategory.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<TextAppCategory> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select textAppCategory from TextAppCategory textAppCategory where textAppCategory.parent = :parent order by textAppCategory.order asc";
			query = entityManager.createQuery(jpql, TextAppCategory.class).setParameter("parent", textAppCategory);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序分类
	 * 
	 * @param productCategories
	 *            分类
	 */
	private void sort(List<TextAppCategory> productCategories) {
		if (CollectionUtils.isEmpty(productCategories)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (TextAppCategory textAppCategory : productCategories) {
			orderMap.put(textAppCategory.getId(), textAppCategory.getOrder());
		}
		Collections.sort(productCategories, new Comparator<TextAppCategory>() {
			@Override
			public int compare(TextAppCategory textAppCategory1, TextAppCategory textAppCategory2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(textAppCategory1.getParentIds(), textAppCategory1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(textAppCategory2.getParentIds(), textAppCategory2.getId());
				Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
				Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
				CompareToBuilder compareToBuilder = new CompareToBuilder();
				while (iterator1.hasNext() && iterator2.hasNext()) {
					Long id1 = iterator1.next();
					Long id2 = iterator2.next();
					Integer order1 = orderMap.get(id1);
					Integer order2 = orderMap.get(id2);
					compareToBuilder.append(order1, order2).append(id1, id2);
					if (!iterator1.hasNext() || !iterator2.hasNext()) {
						compareToBuilder.append(textAppCategory1.getGrade(), textAppCategory2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

}