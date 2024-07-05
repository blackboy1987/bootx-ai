package com.bootx.service.impl;

import com.bootx.dao.TextAppDao;
import com.bootx.entity.TextApp;
import com.bootx.service.TextAppService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class TextAppServiceImpl extends BaseServiceImpl<TextApp,Long> implements TextAppService {

    @Resource
    private TextAppDao textAppDao;

    @Override
    public TextApp findByName(String name) {
        return textAppDao.find("name",name);
    }
}
