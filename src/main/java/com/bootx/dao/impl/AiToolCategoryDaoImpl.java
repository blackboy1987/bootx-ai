package com.bootx.dao.impl;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.dao.AiToolCategoryDao;
import com.bootx.entity.AiToolCategory;
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
public class AiToolCategoryDaoImpl extends BaseDaoImpl<AiToolCategory,Long> implements AiToolCategoryDao {
    @Override
    public List<AiToolCategory> findList(Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AiToolCategory> criteriaQuery = criteriaBuilder.createQuery(AiToolCategory.class);
        Root<AiToolCategory> root = criteriaQuery.from(AiToolCategory.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }

    @Override
    public List<AiToolCategory> findRoots(Integer count) {
        String jpql = "select aiToolCategory from AiToolCategory aiToolCategory where aiToolCategory.parent is null order by aiToolCategory.order asc";
        TypedQuery<AiToolCategory> query = entityManager.createQuery(jpql, AiToolCategory.class);
        if (count != null) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public List<AiToolCategory> findParents(AiToolCategory aiToolCategory, boolean recursive, Integer count) {
        if (aiToolCategory == null || aiToolCategory.getParent() == null) {
            return Collections.emptyList();
        }
        TypedQuery<AiToolCategory> query;
        if (recursive) {
            String jpql = "select aiToolCategory from AiToolCategory aiToolCategory where aiToolCategory.id in (:ids) order by aiToolCategory.grade asc";
            query = entityManager.createQuery(jpql, AiToolCategory.class).setParameter("ids", Arrays.asList(aiToolCategory.getParentIds()));
        } else {
            String jpql = "select aiToolCategory from AiToolCategory aiToolCategory where aiToolCategory = :aiToolCategory";
            query = entityManager.createQuery(jpql, AiToolCategory.class).setParameter("aiToolCategory", aiToolCategory.getParent());
        }
        if (count != null) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public List<AiToolCategory> findChildren(AiToolCategory aiToolCategory, boolean recursive, Integer count) {
        TypedQuery<AiToolCategory> query;
        if (recursive) {
            if (aiToolCategory != null) {
                String jpql = "select aiToolCategory from AiToolCategory aiToolCategory where aiToolCategory.treePath like :treePath order by aiToolCategory.grade asc, aiToolCategory.order asc";
                query = entityManager.createQuery(jpql, AiToolCategory.class).setParameter("treePath", "%" + AiToolCategory.TREE_PATH_SEPARATOR + aiToolCategory.getId() + aiToolCategory.TREE_PATH_SEPARATOR + "%");
            } else {
                String jpql = "select aiToolCategory from AiToolCategory aiToolCategory order by aiToolCategory.grade asc, aiToolCategory.order asc";
                query = entityManager.createQuery(jpql, AiToolCategory.class);
            }
            if (count != null) {
                query.setMaxResults(count);
            }
            List<AiToolCategory> result = query.getResultList();
            sort(result);
            return result;
        } else {
            String jpql = "select aiToolCategory from AiToolCategory aiToolCategory where aiToolCategory.parent = :parent order by aiToolCategory.order asc";
            query = entityManager.createQuery(jpql, AiToolCategory.class).setParameter("parent", aiToolCategory);
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
    private void sort(List<AiToolCategory> productCategories) {
        if (CollectionUtils.isEmpty(productCategories)) {
            return;
        }
        final Map<Long, Integer> orderMap = new HashMap<>();
        for (AiToolCategory aiToolCategory : productCategories) {
            orderMap.put(aiToolCategory.getId(), aiToolCategory.getOrder());
        }
        Collections.sort(productCategories, new Comparator<AiToolCategory>() {
            @Override
            public int compare(AiToolCategory aiToolCategory1, AiToolCategory aiToolCategory2) {
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
