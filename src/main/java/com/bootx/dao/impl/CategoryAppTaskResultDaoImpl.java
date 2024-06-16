package com.bootx.dao.impl;

import com.bootx.dao.CategoryAppTaskResultDao;
import com.bootx.entity.CategoryAppTask;
import com.bootx.entity.CategoryAppTaskResult;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * Entity-CategoryAppTaskDaoImpl
 *
 * @author 一枚猿：blackboyhjy1987
 */
@Repository
public class CategoryAppTaskResultDaoImpl extends BaseDaoImpl<CategoryAppTaskResult,Long> implements CategoryAppTaskResultDao {
    @Override
    public CategoryAppTaskResult findByCategoryAppTask(CategoryAppTask categoryAppTask) {
        if(categoryAppTask==null){
            return null;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoryAppTaskResult> criteriaQuery = criteriaBuilder.createQuery(CategoryAppTaskResult.class);
        Root<CategoryAppTaskResult> root = criteriaQuery.from(CategoryAppTaskResult.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("categoryAppTask"), categoryAppTask));
        try {
            return super.findList(criteriaQuery).getFirst();
        }catch (Exception e){
            return null;
        }
    }
}
