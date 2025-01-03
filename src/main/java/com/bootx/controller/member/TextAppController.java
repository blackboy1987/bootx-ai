package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppTask;
import com.bootx.service.MemberService;
import com.bootx.service.TextAppService;
import com.bootx.service.TextAppTaskService;
import com.bootx.util.AiUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.MessagePojo;
import com.fasterxml.jackson.annotation.JsonView;
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
@RestController("memberTextAppController")
@RequestMapping("/api/member/text_app")
public class TextAppController extends BaseController {

    @Resource
    private TextAppService textAppService;

    @PostMapping(value = "/info")
    @JsonView(TextApp.InfoView.class)
    public Result info(Long textAppId) {
        return Result.success(textAppService.find(textAppId));
    }
}
