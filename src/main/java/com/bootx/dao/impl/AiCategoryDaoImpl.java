package com.bootx.dao.impl;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.dao.AiCategoryDao;
import com.bootx.dao.AiToolCategoryDao;
import com.bootx.entity.AiCategory;
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
 * @author blackboy1987
 */
@Repository
public class AiCategoryDaoImpl extends BaseDaoImpl<AiCategory,Long> implements AiCategoryDao {
    @Override
    public List<AiCategory> findList(Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AiCategory> criteriaQuery = criteriaBuilder.createQuery(AiCategory.class);
        Root<AiCategory> root = criteriaQuery.from(AiCategory.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }

    @Override
    public List<AiCategory> findRoots(Integer count) {
        String jpql = "select aiCategory from AiCategory aiCategory where aiCategory.parent is null order by aiCategory.order asc";
        TypedQuery<AiCategory> query = entityManager.createQuery(jpql, AiCategory.class);
        if (count != null) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public List<AiCategory> findParents(AiCategory aiCategory, boolean recursive, Integer count) {
        if (aiCategory == null || aiCategory.getParent() == null) {
            return Collections.emptyList();
        }
        TypedQuery<AiCategory> query;
        if (recursive) {
            String jpql = "select aiCategory from AiCategory aiCategory where aiCategory.id in (:ids) order by aiCategory.grade asc";
            query = entityManager.createQuery(jpql, AiCategory.class).setParameter("ids", Arrays.asList(aiCategory.getParentIds()));
        } else {
            String jpql = "select aiCategory from AiCategory aiCategory where aiCategory = :aiCategory";
            query = entityManager.createQuery(jpql, AiCategory.class).setParameter("aiCategory", aiCategory.getParent());
        }
        if (count != null) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public List<AiCategory> findChildren(AiCategory aiCategory, boolean recursive, Integer count) {
        TypedQuery<AiCategory> query;
        if (recursive) {
            if (aiCategory != null) {
                String jpql = "select aiCategory from AiCategory aiCategory where aiCategory.treePath like :treePath order by aiCategory.grade asc, aiCategory.order asc";
                query = entityManager.createQuery(jpql, AiCategory.class).setParameter("treePath", "%" + AiCategory.TREE_PATH_SEPARATOR + aiCategory.getId() + aiCategory.TREE_PATH_SEPARATOR + "%");
            } else {
                String jpql = "select aiCategory from AiCategory aiCategory order by aiCategory.grade asc, aiCategory.order asc";
                query = entityManager.createQuery(jpql, AiCategory.class);
            }
            if (count != null) {
                query.setMaxResults(count);
            }
            List<AiCategory> result = query.getResultList();
            sort(result);
            return result;
        } else {
            String jpql = "select aiCategory from AiCategory aiCategory where aiCategory.parent = :parent order by aiCategory.order asc";
            query = entityManager.createQuery(jpql, AiCategory.class).setParameter("parent", aiCategory);
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
    private void sort(List<AiCategory> productCategories) {
        if (CollectionUtils.isEmpty(productCategories)) {
            return;
        }
        final Map<Long, Integer> orderMap = new HashMap<>();
        for (AiCategory aiCategory : productCategories) {
            orderMap.put(aiCategory.getId(), aiCategory.getOrder());
        }
        Collections.sort(productCategories, new Comparator<AiCategory>() {
            @Override
            public int compare(AiCategory aiToolCategory1, AiCategory aiToolCategory2) {
                Long[] ids1 = (Long[]) ArrayUtils.add(aiToolCategory1.getParentIds(), aiToolCategory1.getId());
                Long[] ids2 = (Long[]) ArrayUtils.add(aiToolCategory2.getParentIds(), aiToolCategory2.getId());
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
                        compareToBuilder.append(aiToolCategory1.getGrade(), aiToolCategory2.getGrade());
                    }
                }
                return compareToBuilder.toComparison();
            }
        });
    }
}
