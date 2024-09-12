package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppTask;
import com.bootx.service.MemberService;
import com.bootx.service.TextAppService;
import com.bootx.service.TextAppTaskService;
import com.bootx.util.AiUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.MessagePojo;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    public Result write(Long textAppId, String params) {
        TextApp textApp = textAppService.find(textAppId);
        if(textApp==null){
            return Result.error("应用不存在");
        }
        Member member = memberService.find(1L);
        TextAppTask textAppTask = textAppTaskService.create(textApp, member, params);
        textAppTaskService.start(textAppTask);
        redisService.set("textAppTask:"+textAppTask.getTaskId(),"",60, TimeUnit.MINUTES);
        return Result.success(textAppTask.getTaskId());
    }

    @PostMapping(value = "/load",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> load(String taskId){
        TextAppTask textAppTask = textAppTaskService.findByTaskId(taskId);
        if(textAppTask==null||textAppTask.getStatus()!=1){
            return Flux.empty();
        }
        return Flux.from(Objects.requireNonNull(AiUtils.message(textAppTask.getPrompt(),textAppTask.getTextApp().getUserPrompt()))).takeUntil(item-> {
            MessagePojo messagePojo = JsonUtils.toObject(item, new TypeReference<MessagePojo>() {
            });
            if(StringUtils.equalsIgnoreCase(messagePojo.getFinishReason(),"stop")){
                // 任务完成
                textAppTask.setStatus(2);
                textAppTaskService.update(textAppTask);
                return true;
            }
            return false;
        }).delayElements(Duration.ofMillis(10));
    }

    @GetMapping(value = "/msg",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> msg(String content){
        return Flux.from(Objects.requireNonNull(AiUtils.message(content,""))).takeUntil(item-> {
            MessagePojo messagePojo = JsonUtils.toObject(item, new TypeReference<MessagePojo>() {
            });
            return StringUtils.equalsIgnoreCase(messagePojo.getFinishReason(), "stop");
        }).delayElements(Duration.ofMillis(10));
    }
}
