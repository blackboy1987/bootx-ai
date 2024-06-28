package com.bootx.controller.member;

import com.bootx.controller.BaseController;
import com.bootx.service.ImageTaskService;
import com.bootx.service.MemberService;
import com.bootx.service.SmsLogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
