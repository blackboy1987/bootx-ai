package com.bootx.service;

import com.bootx.entity.TextApp;

/**
 * @author black
 */
public interface TextAppService extends BaseService<TextApp,Long> {
    TextApp findByName(String name);
}
