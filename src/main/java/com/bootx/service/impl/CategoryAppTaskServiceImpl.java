package com.bootx.service.impl;

import com.bootx.entity.CategoryApp;
import com.bootx.entity.CategoryAppTask;
import com.bootx.entity.Member;
import com.bootx.service.CategoryAppTaskService;
import com.bootx.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Entity-CategoryAppTaskResultServiceImp
 *
 * @author 一枚猿：blackboyhjy1987
 */
@Service
public class CategoryAppTaskServiceImpl extends BaseServiceImpl<CategoryAppTask,Long> implements CategoryAppTaskService {

    @Override
    public CategoryAppTask create(CategoryApp categoryApp, Member member, String params) {
        CategoryAppTask categoryAppTask = new CategoryAppTask();
        categoryAppTask.setCategoryApp(categoryApp);
        categoryAppTask.setMember(member);
        categoryAppTask.setParams(params);
        categoryAppTask.setStatus(0);
        categoryAppTask.setTaskId(DateUtils.formatDateToString(new Date(), "yyyyMMddHHmmssSSS") + categoryApp.getId() + member.getId());
        return super.save(categoryAppTask);
    }
}
