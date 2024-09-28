package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppCategory;

/**
 * @author black
 */
public interface TextAppService extends BaseService<TextApp,Long> {
    TextApp findByName(String name);
    Page<TextApp> findPage(Pageable pageable, String name, TextAppCategory textAppCategory);
}
