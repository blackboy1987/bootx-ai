package com.bootx.service.impl;

import com.bootx.dao.TextAppTaskDao;
import com.bootx.entity.Member;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppTask;
import com.bootx.service.TextAppTaskService;
import com.bootx.util.DateUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.MessagePojo;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity-CategoryAppTaskResultServiceImp
 *
 * @author 一枚猿：blackboyhjy1987
 */
@Service
public class TextAppTaskServiceImpl extends BaseServiceImpl<TextAppTask,Long> implements TextAppTaskService {

    @Resource
    private TextAppTaskDao textAppTaskDao;

    @Override
    public TextAppTask create(TextApp textApp, Member member, String params) {
        TextAppTask textAppTask = new TextAppTask();
        textAppTask.setTextApp(textApp);
        textAppTask.setMember(member);
        textAppTask.setParams(params);
        textAppTask.setStatus(0);
        Map<String, Object> map = JsonUtils.toObject(textAppTask.getParams(), new TypeReference<Map<String, Object>>() {
        });
        String prompt = textApp.getUserPrompt();
        for (String key : map.keySet()) {
            prompt = prompt.replace("{" + key + "}", map.get(key).toString());
        }
        textAppTask.setPrompt(prompt);
        textAppTask.setTaskId(DateUtils.formatDateToString(new Date(), "yyyyMMddHHmmssSSS") + member.getId());
        return super.save(textAppTask);
    }

    @Override
    public void error(TextAppTask textAppTask) {
        textAppTask.setStatus(4);
        super.update(textAppTask);
    }

    @Override
    public void complete(TextAppTask textAppTask) {
        textAppTask.setStatus(2);
        super.update(textAppTask);
    }

    @Override
    public void start(TextAppTask textAppTask) {
        textAppTask.setStatus(1);
        textAppTask.setResult("");
        super.update(textAppTask);
    }

    @Override
    public TextAppTask findByTaskId(String taskId) {
        return textAppTaskDao.find("taskId",taskId);
    }

    @Override
    public void load(TextAppTask textAppTask, MessagePojo messagePojo) {
        textAppTask.setResult(textAppTask.getResult()+messagePojo.getContent());
        super.update(textAppTask);
    }
}
