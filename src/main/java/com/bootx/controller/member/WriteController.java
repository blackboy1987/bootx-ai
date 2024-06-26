package com.bootx.controller.member;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppTask;
import com.bootx.security.CurrentUser;
import com.bootx.service.CategoryAppTaskService;
import com.bootx.service.TextAppService;
import com.bootx.service.TextAppTaskService;
import com.bootx.util.JsonUtils;
import com.bootx.util.ali.TextUtils;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/member/write")
public class WriteController extends BaseController {

    @Resource
    private TextAppService textAppService;

    @Resource
    private TextAppTaskService textAppTaskService;

    @PostMapping(value = "/write")
    public Result write(Long textAppId, String content, Integer count, @CurrentUser Member member) {
        TextApp textApp = textAppService.find(textAppId);
        if(textApp==null){
            return Result.error("应用不存在");
        }
        Map<String,Object> params = new HashMap<>();
        params.put("content",content);
        params.put("count",count);
        TextAppTask textAppTask = textAppTaskService.create(textApp, member, JsonUtils.toJson(params));
        textAppTaskService.start(textAppTask);
        TextUtils.streamCallWithCallback(textAppTask.getPrompt(),messagePojo -> {
            textAppTaskService.load(textAppTask,messagePojo);
        },err->{
            textAppTaskService.error(textAppTask);
        },status->{
            textAppTaskService.complete(textAppTask);
        });

        return Result.success(textAppTask.getId());
    }

    @PostMapping(value = "/load")
    public Result load(@RequestHeader String deviceId, String taskId){
        TextAppTask textAppTask = textAppTaskService.findByTaskId(taskId);
        return Result.success(textAppTask.getResult());
    }
}
