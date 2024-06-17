package com.bootx.service.impl;

import com.bootx.dao.CategoryAppDao;
import com.bootx.entity.CategoryApp;
import com.bootx.service.CategoryAppService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class CategoryAppServiceImpl extends BaseServiceImpl<CategoryApp,Long> implements CategoryAppService {

    @Resource
    private CategoryAppDao categoryAppDao;

    @Override
    public CategoryApp findByName(String name) {
        System.out.println(name);
        return categoryAppDao.find("title",name);
    }
}
