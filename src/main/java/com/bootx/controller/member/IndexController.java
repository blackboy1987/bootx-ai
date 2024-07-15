package com.bootx.controller.member;

import com.bootx.controller.BaseController;
import com.bootx.service.ImageTaskService;
import com.bootx.service.MemberService;
import com.bootx.service.SmsLogService;
import com.bootx.util.AiUtils;
import com.bootx.util.MessagePojo;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

/**
 * @author black
 */
@RestController("memberIndexController")
@RequestMapping("/api/member")
public class IndexController extends BaseController {

    @Resource
    private MemberService memberService;

    @Resource
    private ImageTaskService imageTaskService;

    @Resource
    private SmsLogService smsLogService;

    @GetMapping(value = "/message",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessagePojo> message(String content){
        return Flux.from(Objects.requireNonNull(AiUtils.message1(content)));
    }


}
