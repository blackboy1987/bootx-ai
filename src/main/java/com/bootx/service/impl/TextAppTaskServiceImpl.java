package com.bootx.service.impl;

import com.bootx.dao.TextAppTaskDao;
import com.bootx.entity.Member;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppTask;
import com.bootx.service.TextAppTaskService;
import com.bootx.util.DateUtils;
import com.bootx.util.MessagePojo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        textAppTask.setPrompt("你是谁？");
        textAppTask.setTaskId(DateUtils.formatDateToString(new Date(), "yyyyMMddHHmmssSSS") + textAppTask.getId() + member.getId());
        return super.save(textAppTask);
    }

    @Override
    public void error(TextAppTask textAppTask) {
        textAppTask.setStatus(4);
        textAppTask.setTaskEndDate(new Date());
        super.update(textAppTask);
    }

    @Override
    public void complete(TextAppTask textAppTask) {
        textAppTask.setStatus(2);
        textAppTask.setTaskEndDate(new Date());
        super.update(textAppTask);
    }

    @Override
    public void start(TextAppTask textAppTask) {
        textAppTask.setStatus(1);
        textAppTask.setResult("");
        textAppTask.setTaskBeginDate(new Date());
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
