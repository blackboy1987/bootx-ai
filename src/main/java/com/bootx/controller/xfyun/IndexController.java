package com.bootx.controller.xfyun;

import com.bootx.controller.BaseController;
import com.bootx.util.xfyun.XFYunUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

/**
 * @author black
 */
@RestController("apiXFYunController")
@RequestMapping("/api/xfyun")
public class IndexController extends BaseController {

    @PostMapping(value = "/message",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> message(String content) {
        return Flux.from(Objects.requireNonNull(XFYunUtils.text(content))).takeUntil(item-> {
            System.out.println(item);
            return true;
        }).delayElements(Duration.ofMillis(200));
    }
}
