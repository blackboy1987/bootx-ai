package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.security.CurrentUser;
import com.bootx.service.ImageTaskService;
import com.bootx.service.MemberService;
import com.bootx.service.SmsLogService;
import com.bootx.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@RestController("memberIndexController")
@RequestMapping("/api/member")
public class IndexController extends BaseController {

    @Resource
    private MemberService memberService;

    @Resource
    private SmsLogService smsLogService;

    @GetMapping(value = "/message",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessagePojo> message(String content){
        return Flux.from(Objects.requireNonNull(AiUtils.message(content,"")));
    }

    /**
     * 登录
     * @param deviceId
     * @param mobile
     * @param code
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(@RequestHeader String deviceId, String mobile, String code){
        Member member = memberService.findByMobile(mobile);
        String s = redisService.get("login:" + mobile + ":" + deviceId);
        if(!StringUtils.equalsAnyIgnoreCase(s,code)){
            return Result.error("验证码输入错误");
        }
        if(member.getIsLocked()){
            member.setIsLocked(false);
            member.setLockDate(null);
            memberService.update(member);
        }
        return Result.success(JWTUtils.create(member.getId()+"",new HashMap<>()));
    }

    /**
     * 发送验证码
     * @param deviceId
     * @param mobile
     * @return
     */
    @PostMapping(value = "/sendCode")
    public Result sendCode(@RequestHeader String deviceId, String mobile,Integer type, HttpServletRequest request){
        if(type==null){
            return Result.error("参数异常");
        }
        Member member = memberService.create(mobile, deviceId);
        if(member!=null && StringUtils.equalsAnyIgnoreCase(member.getMobile(),mobile)){
            String code = CodeUtils.getCode(6);
            String result = SmsUtils.send(mobile,code);
            if(type==0){
                redisService.set("login:"+mobile+":"+deviceId,code,10, TimeUnit.MINUTES);
            }else if(type==1){
                redisService.set("updateMobile:"+mobile+":"+deviceId,code,10, TimeUnit.MINUTES);
            }

            smsLogService.create(member,deviceId,IPUtils.getIpAddr(request),result,code);
            return Result.success();
        }
        return Result.error("信息校验失败");
    }

    @PostMapping(value = "/currentUser")
    public Result currentUser(@CurrentUser Member member){
        if(member==null ||member.getIsLocked()){
            return Result.error("未登录");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("username",member.getUsername());
        data.put("mobile",member.getMobile());
        data.put("isVip",!member.getMemberRank().getIsDefault());
        data.put("expiredDate",member.getMemberRankExpiredDate());
        return Result.success(data);
    }

    @PostMapping(value = "/logout")
    public Result logout(@CurrentUser Member member){
        if(member==null){
            return Result.error("未登录");
        }
        member.setIsLocked(true);
        member.setLockDate(new Date());
        memberService.update(member);
        return Result.success();
    }


    @PostMapping(value = "/updateUserInfo")
    public Result updateUserInfo(@CurrentUser Member member,String username){
        if(member==null){
            return Result.error("未登录");
        }
        if(!memberService.usernameUnique(member.getId(),username)){
            return Result.error("用户名已存在");
        }
        member.setUsername(username);
        memberService.update(member);
        return Result.success();
    }

    @PostMapping(value = "/updateMobile")
    public Result updateMobile(@CurrentUser Member member,String mobile,String code,HttpServletRequest request){
        String deviceId = request.getHeader("deviceId");
        if(member==null){
            return Result.error("未登录");
        }
        String s = redisService.get("updateMobile:" + mobile + ":" + deviceId);
        if(!StringUtils.equalsAnyIgnoreCase(s,code)){
            return Result.error("验证码输入错误");
        }
        if(!memberService.mobileUnique(member.getId(),mobile)){
            return Result.error("手机号已存在");
        }
        member.setMobile(mobile);
        memberService.update(member);
        return Result.success();
    }

    @PostMapping(value = "/reward")
    public Result reward(@CurrentUser Member member,String mobile,String code,HttpServletRequest request){
        String deviceId = request.getHeader("deviceId");
        if(member==null){
            return Result.error("未登录");
        }
        MemberRank memberRank = member.getMemberRank();
        if(memberRank!=null){
            if(member.hasExpired()){
                member.setMemberRankExpiredDate(DateUtils.getNextDay(1));
            }else{
                member.setMemberRankExpiredDate(DateUtils.getNextDay(member.getMemberRankExpiredDate(),1));
            }
            memberService.update(member);
        }

        return Result.success();
    }


}
