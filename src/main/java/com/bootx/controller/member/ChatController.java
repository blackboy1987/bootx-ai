package com.bootx.controller.member;

import com.bootx.controller.BaseController;
import com.bootx.util.ali2.TextChat;
import com.bootx.util.ali2.text.TextResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController extends BaseController {

    @PostMapping(value = "/message",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TextResponse> message(String content){
        if(StringUtils.isBlank(content)){
            return Flux.empty();
        }
        return Flux.from(Objects.requireNonNull(TextChat.chat(content)));
    }
}
