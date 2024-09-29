package com.bootx.service.impl;

import com.bootx.dao.AiToolDao;
import com.bootx.entity.AiTool;
import com.bootx.service.AiToolService;
import com.bootx.util.JsonUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class AiToolServiceImpl extends BaseServiceImpl<AiTool,Long> implements AiToolService {

    @Resource
    private AiToolDao aiToolDao;

    @Override
    public void create(AiTool aiTool) {
        if(aiTool==null || StringUtils.isEmpty(aiTool.getOtherUrl())){
            return;
        }
        AiTool aiTool1 = aiToolDao.find("otherUrl", aiTool.getOtherUrl());
        if(aiTool1==null){
            super.save(aiTool);
        }else{
            if(StringUtils.isNotBlank(aiTool.getName())){
                aiTool1.setName(aiTool.getName());
            }
            if(StringUtils.isNotBlank(aiTool.getIcon())){
                aiTool1.setIcon(aiTool.getIcon());
            }
            if(StringUtils.isNotBlank(aiTool.getMemo())){
                aiTool1.setMemo(aiTool.getMemo());
            }
            if(aiTool.getAiToolCategory()!=null){
                aiTool1.setAiToolCategory(aiTool.getAiToolCategory());
            }
            if(StringUtils.isNotBlank(aiTool.getDescription())){
                aiTool1.setDescription(aiTool.getDescription());
            }
            if(StringUtils.isNotBlank(aiTool.getTypeId())){
                aiTool1.setTypeId(aiTool.getTypeId());
            }
            if(StringUtils.isNotBlank(aiTool.getType())){
                aiTool1.setType(aiTool.getType());
            }
            if(StringUtils.isNotBlank(aiTool.getOtherUrl())){
                aiTool1.setOtherUrl(aiTool.getOtherUrl());
            }
            if(StringUtils.isNotBlank(aiTool.getTag())){
                aiTool1.setTag(aiTool.getTag());
            }
            if(StringUtils.isNotBlank(aiTool.getCover())){
                aiTool1.setCover(aiTool.getCover());
            }
            if(StringUtils.isNotBlank(aiTool.getUrl())){
                aiTool1.setUrl(aiTool.getUrl());
            }
            super.update(aiTool1);
        }
    }
}
