
package com.bootx.dao.impl;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.dao.CategoryDao;
import com.bootx.entity.Category;
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
 * Dao - 商品分类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class CategoryDaoImpl extends BaseDaoImpl<Category, Long> implements CategoryDao {

	@Override
	public List<Category> findList(Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
		Root<Category> root = criteriaQuery.from(Category.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}

	@Override
	public List<Category> findRoots(Integer count) {
		String jpql = "select category from Category category where category.parent is null order by category.order asc";
		TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Category> findParents(Category category, boolean recursive, Integer count) {
		if (category == null || category.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<Category> query;
		if (recursive) {
			String jpql = "select category from Category category where category.id in (:ids) order by category.grade asc";
			query = entityManager.createQuery(jpql, Category.class).setParameter("ids", Arrays.asList(category.getParentIds()));
		} else {
			String jpql = "select category from Category category where category = :category";
			query = entityManager.createQuery(jpql, Category.class).setParameter("category", category.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Category> findChildren(Category category, boolean recursive, Integer count) {
		TypedQuery<Category> query;
		if (recursive) {
			if (category != null) {
				String jpql = "select category from Category category where category.treePath like :treePath order by category.grade asc, category.order asc";
				query = entityManager.createQuery(jpql, Category.class).setParameter("treePath", "%" + Category.TREE_PATH_SEPARATOR + category.getId() + Category.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select category from Category category order by category.grade asc, category.order asc";
				query = entityManager.createQuery(jpql, Category.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<Category> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select category from Category category where category.parent = :parent order by category.order asc";
			query = entityManager.createQuery(jpql, Category.class).setParameter("parent", category);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 */
	private void sort(List<Category> productCategories) {
		if (CollectionUtils.isEmpty(productCategories)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (Category category : productCategories) {
			orderMap.put(category.getId(), category.getOrder());
		}
		Collections.sort(productCategories, new Comparator<Category>() {
			@Override
			public int compare(Category category1, Category category2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(category1.getParentIds(), category1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(category2.getParentIds(), category2.getId());
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
						compareToBuilder.append(category1.getGrade(), category2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

}