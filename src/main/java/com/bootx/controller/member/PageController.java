package com.bootx.controller.member;

import com.bootx.controller.BaseController;
import com.bootx.service.ImageTaskService;
import com.bootx.service.MemberService;
import com.bootx.service.SmsLogService;
import com.bootx.util.AiUtils;
import com.bootx.util.MessagePojo;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

/**
 * @author black
 */
@Controller("memberPageController")
@RequestMapping("/member")
public class PageController extends BaseController {

    @GetMapping("/**")
    public String index(){
        return "/member/index";
    }
}
