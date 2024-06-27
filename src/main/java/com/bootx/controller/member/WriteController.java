package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppTask;
import com.bootx.service.MemberService;
import com.bootx.service.TextAppService;
import com.bootx.service.TextAppTaskService;
import com.bootx.util.JsonUtils;
import com.bootx.util.ali.TextUtils;
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
    @Resource
    private MemberService memberService;

    @PostMapping(value = "/text")
    public Result write(Long textAppId, String content, Integer count) {
        TextApp textApp = textAppService.find(textAppId);
        if(textApp==null){
            return Result.error("应用不存在");
        }
        Member member = memberService.find(1L);
        Map<String,Object> params = new HashMap<>();
        params.put("content",content);
        params.put("count",count);
        TextAppTask textAppTask = textAppTaskService.create(textApp, member, JsonUtils.toJson(params));
        textAppTaskService.start(textAppTask);
        redisService.set("textAppTask:"+textAppTask.getTaskId(),"");
        TextUtils.streamCallWithCallback(textAppTask.getPrompt(),messagePojo -> {
            System.out.println(JsonUtils.toJson(messagePojo));
            redisService.set("textAppTask:"+textAppTask.getTaskId(),redisService.get("textAppTask:"+textAppTask.getTaskId())+messagePojo.getContent());
            //textAppTaskService.load(textAppTask,messagePojo);
        },err->{
            //textAppTaskService.error(textAppTask);
        },status->{
            //textAppTaskService.complete(textAppTask);
        });

        return Result.success(textAppTask.getTaskId());
    }

    @PostMapping(value = "/load")
    public Result load(@RequestHeader String deviceId, String taskId){
        return Result.success(redisService.get("textAppTask:"+taskId));
    }
}
