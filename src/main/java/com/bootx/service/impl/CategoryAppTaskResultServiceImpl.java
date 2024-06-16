package com.bootx.service.impl;

import com.bootx.dao.CategoryAppTaskResultDao;
import com.bootx.entity.CategoryAppTask;
import com.bootx.entity.CategoryAppTaskResult;
import com.bootx.service.CategoryAppTaskResultService;
import com.bootx.util.MessagePojo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Entity-CategoryAppTaskResultServiceImp
 *
 * @author 一枚猿：blackboyhjy1987
 */
@Service
public class CategoryAppTaskResultServiceImpl extends BaseServiceImpl<CategoryAppTaskResult,Long> implements CategoryAppTaskResultService {

    @Resource
    private CategoryAppTaskResultDao categoryAppTaskResultDao;

    @Override
    public CategoryAppTaskResult create(CategoryAppTask categoryAppTask, MessagePojo messagePojo) {
        if(categoryAppTask==null){
            return null;
        }
        CategoryAppTaskResult categoryAppTaskResult = findByCategoryAppTask(categoryAppTask);
        if(categoryAppTaskResult==null){
            categoryAppTaskResult=new CategoryAppTaskResult();
            categoryAppTaskResult.setCategoryAppTask(categoryAppTask);
        }

        categoryAppTaskResult.setContent(categoryAppTaskResult.getContent()+messagePojo.getContent());
        categoryAppTaskResult.setInputTokens(messagePojo.getInputTokens());
        categoryAppTaskResult.setOutputTokens(messagePojo.getOutputTokens());
        categoryAppTaskResult.setTotalTokens(messagePojo.getTotalTokens());
        if(categoryAppTaskResult.isNew()){
            return super.save(categoryAppTaskResult);
        }
        return super.update(categoryAppTaskResult);
    }

    private CategoryAppTaskResult findByCategoryAppTask(CategoryAppTask categoryAppTask) {
        if (categoryAppTask != null) {
            return categoryAppTaskResultDao.findByCategoryAppTask(categoryAppTask);
        }
        return null;
    }
}
