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
import java.util.concurrent.atomic.AtomicReference;

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
    public Flux<MessagePojo> load(String taskId){
        redisService.set(taskId+":count",100+"");
        TextAppTask textAppTask = textAppTaskService.findByTaskId(taskId);
        if(textAppTask==null||textAppTask.getStatus()!=1){
            redisService.set(taskId+":count","-1");
            redisService.set(taskId+":"+0,JsonUtils.toJson(MessagePojo.stop()),30,TimeUnit.MINUTES);
            return Flux.empty();
        }
        AtomicReference<Integer> count = new AtomicReference<>(0);
        return Flux.from(Objects.requireNonNull(AiUtils.message(textAppTask.getPrompt(),textAppTask.getTextApp().getUserPrompt()))).takeUntil(item-> {
            redisService.set(taskId+":"+(count.getAndSet(count.get() + 1)),JsonUtils.toJson(item),30,TimeUnit.MINUTES);
            if(StringUtils.equalsIgnoreCase(item.getFinishReason(),"stop")){
                redisService.set(taskId+":"+(count.getAndSet(count.get() + 1)),JsonUtils.toJson(MessagePojo.stop()));
                redisService.set("taskId:count",count.get()+"");
                // 任务完成
                textAppTask.setStatus(2);
                textAppTaskService.update(textAppTask);
                return true;
            }
            return false;
        }).delayElements(Duration.ofMillis(200));
    }

    @GetMapping(value = "/msg",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessagePojo> msg(String content){
        return Flux.from(Objects.requireNonNull(AiUtils.message(content,""))).takeUntil(item-> {
            return StringUtils.equalsIgnoreCase(item.getFinishReason(), "stop");
        }).delayElements(Duration.ofMillis(100));
    }

    @PostMapping(value = "/reload")
    public String reload(String taskId,Integer count){
        String s1 = redisService.get(taskId + ":count");
        if(StringUtils.isBlank(s1) || StringUtils.equalsAnyIgnoreCase("-1",s1) || count>Integer.parseInt(s1)){
            return JsonUtils.toJson(MessagePojo.stop());
        }
        String s = redisService.get(taskId + ":" + count);
        if(StringUtils.isBlank(s)){
            return JsonUtils.toJson(MessagePojo.empty());
        }
        System.out.println(s);
        return s;
    }
}
