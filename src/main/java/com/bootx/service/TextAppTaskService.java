package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppTask;
import com.bootx.util.MessagePojo;

/**
 * Entity-CategoryAppTaskService
 *
 * @author 一枚猿：blackboyhjy1987
 */
public interface TextAppTaskService extends BaseService<TextAppTask,Long> {
    TextAppTask create(TextApp textApp, Member member, String params);

    void error(TextAppTask textAppTask);

    void complete(TextAppTask textAppTask);

    void start(TextAppTask textAppTask);

    TextAppTask findByTaskId(String taskId);

    void load(TextAppTask textAppTask, MessagePojo messagePojo);
}
