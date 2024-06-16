package com.bootx.service;

import com.bootx.entity.CategoryAppTask;
import com.bootx.entity.CategoryAppTaskResult;
import com.bootx.util.MessagePojo;

/**
 * Entity-CategoryAppTaskResultService
 *
 * @author 一枚猿：blackboyhjy1987
 */
public interface CategoryAppTaskResultService extends BaseService<CategoryAppTaskResult, Long> {
    CategoryAppTaskResult create(CategoryAppTask categoryAppTask, MessagePojo messagePojo);
}
