package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.service.MemberService;
import com.bootx.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author black
 */
@RestController("MemberIndexController")
@RequestMapping("/api/member")
public class IndexController extends BaseController {

    @Resource
    private MemberService memberService;

    @GetMapping(value = "/message",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessagePojo> message(String content, HttpServletRequest request){
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String s = parameterNames.nextElement();
            System.out.println(s+":"+request.getParameter(s));
        }
        List<MessagePojo> list = new ArrayList<>();
        final Boolean[] isTop = {false};
        AtomicReference<Integer> index = new AtomicReference<>(0);
        new Thread(()->{
            AiUtils.message(content, message->{
                System.out.println(JsonUtils.toJson(message));
                if (!isTop[0] && StringUtils.isNotBlank(message.getRequestId())){
                    list.add(message);
                    isTop[0] = StringUtils.equalsIgnoreCase(message.getFinishReason(),"stop");
                }
            });
        }).start();

        return Flux.interval(Duration.ofMillis(10)).onBackpressureBuffer().map(sequence -> {
            try {
                return list.get(index.getAndSet(index.get() + 1));
            }catch (Exception e){
                index.set(index.get()-1);
                // 没有结束还有消息，但是list里面的消息已经获取完毕了
                return MessagePojo.empty();
            }
        }).filter(item->!StringUtils.equalsIgnoreCase(item.getFinishReason(),"empty") && StringUtils.isNotEmpty(item.getContent())).takeUntil(item->StringUtils.equalsIgnoreCase(item.getFinishReason(),"stop"));
    }

    @GetMapping(value = "/vl",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessagePojo> vl(String image,String content){
        List<MessagePojo> list = new ArrayList<>();
        final Boolean[] isTop = {false};
        AtomicReference<Integer> index = new AtomicReference<>(0);
        new Thread(()->{
            AiVlUtils.message(image,content, message->{
                System.out.println(new Date());
                if (!isTop[0]){
                    list.add(message);
                    isTop[0] = StringUtils.equalsIgnoreCase(message.getContent(),"stop");
                }
            });
        }).start();
        return Flux.interval(Duration.ofMillis(100)).map(sequence -> {
            // 如果没有结束
            if (!isTop[0]){
                try {
                    return list.get(index.getAndSet(index.get() + 1));
                }catch (Exception e){
                    index.set(index.get()-1);
                    System.out.println(index.get());
                    // 没有结束还有消息，但是list里面的消息已经获取完毕了
                    return new MessagePojo();
                }
            }else {
                return null;
            }
        });
    }
    @PostMapping(value = "/app")
    public Result app(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,thumb,title,memo from categoryapp");
        return Result.success(maps);
    }

    @PostMapping(value = "/sendCode")
    public Result sendCode(@RequestHeader String deviceId,String mobile){
        Member member = memberService.create(mobile, deviceId);
        if(member!=null && StringUtils.equalsAnyIgnoreCase(member.getMobile(),mobile)){
            String code = "1234";
            //String send = SmsUtils.send(mobile,code);
            redisService.set("login:"+mobile+":"+deviceId,code,10, TimeUnit.MINUTES);
            return Result.success();
        }
        return Result.error("信息校验失败");
    }

    @PostMapping(value = "/login")
    public Result login(@RequestHeader String deviceId,String mobile,String code){
        Member member = memberService.findByMobile(mobile);
        String s = redisService.get("login:" + mobile + ":" + deviceId);
        if(!StringUtils.equalsAnyIgnoreCase(s,code)){
            return Result.error("验证码输入错误");
        }
        return Result.success(JWTUtils.create(member.getId()+"",new HashMap<>()));
    }

    @PostMapping(value = "/load")
    public Result load(@RequestHeader String deviceId,String taskId){
        return Result.success();
    }

}
