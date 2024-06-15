package com.bootx.service;

import com.bootx.entity.CategoryApp;
import com.bootx.entity.CategoryAppTask;
import com.bootx.entity.Member;

/**
 * Entity-CategoryAppTaskService
 *
 * @author 一枚猿：blackboyhjy1987
 */
public interface CategoryAppTaskService extends BaseService<CategoryAppTask,Long> {
    CategoryAppTask create(CategoryApp categoryApp, Member member, String params);
}
