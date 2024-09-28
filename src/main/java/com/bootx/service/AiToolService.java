
package com.bootx.service;

import com.bootx.entity.AiTool;

/**
 * Service - 商品分类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AiToolService extends BaseService<AiTool, Long> {
    void create(AiTool aiTool);
}