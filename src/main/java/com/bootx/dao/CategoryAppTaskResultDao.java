package com.bootx.dao;

import com.bootx.entity.CategoryAppTask;
import com.bootx.entity.CategoryAppTaskResult;

/**
 * Entity-CategoryAppTaskDao
 *
 * @author 一枚猿：blackboyhjy1987
 */
public interface CategoryAppTaskResultDao extends BaseDao<CategoryAppTaskResult,Long>{
    CategoryAppTaskResult findByCategoryAppTask(CategoryAppTask categoryAppTask);
}
