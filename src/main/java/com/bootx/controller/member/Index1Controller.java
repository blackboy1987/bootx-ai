package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.ImageTaskService;
import com.bootx.service.MemberService;
import com.bootx.service.SmsLogService;
import com.bootx.util.*;
import com.bootx.util.ali.AliCommonUtils;
import com.bootx.util.ali.TextToImageUtils;
import jakarta.annotation.Resource;
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
@RestController("member1IndexController")
@RequestMapping("/api/member1")
public class Index1Controller extends BaseController {

    @Resource
    private MemberService memberService;

    @Resource
    private ImageTaskService imageTaskService;

    @Resource
    private SmsLogService smsLogService;

    @GetMapping(value = "/message",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessagePojo> message(String content){
        List<MessagePojo> list = new ArrayList<>();
        AtomicReference<Integer> index = new AtomicReference<>(0);
        new Thread(()->AiUtils.message(content, list::add)).start();
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

    /**
     * 发送验证码
     * @param deviceId
     * @param mobile
     * @return
     */
    @PostMapping(value = "/sendCode")
    public Result sendCode(@RequestHeader String deviceId,@RequestHeader String ip,String mobile){
        Member member = memberService.create(mobile, deviceId);
        if(member!=null && StringUtils.equalsAnyIgnoreCase(member.getMobile(),mobile)){
            String code = "1234";
            String result = SmsUtils.send(mobile,code);
            redisService.set("login:"+mobile+":"+deviceId,code,10, TimeUnit.MINUTES);
            smsLogService.create(member,deviceId,ip,result,code);
            return Result.success();
        }
        return Result.error("信息校验失败");
    }

    /**
     * 登录
     * @param deviceId
     * @param mobile
     * @param code
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(@RequestHeader String deviceId,String mobile,String code){
        Member member = memberService.findByMobile(mobile);
        String s = redisService.get("login:" + mobile + ":" + deviceId);
        if(!StringUtils.equalsAnyIgnoreCase(s,code)){
            return Result.error("验证码输入错误");
        }
        return Result.success(JWTUtils.create(member.getId()+"",new HashMap<>()));
    }


    @PostMapping(value = "/text2image")
    public Result textToImage(@RequestHeader String deviceId,@CurrentUser Member member,String prompt,String style,String size){
        if(StringUtils.isEmpty(prompt)){
            return Result.error("请输入提示词");
        }
        if(StringUtils.isEmpty(style)){
            return Result.error("请选择图片风格");
        }
        if(StringUtils.isEmpty(size)){
            return Result.error("请选择图片大小");
        }
        // 写入任务
        TextToImageUtils.Output output = TextToImageUtils.create(prompt, style, size);
        imageTaskService.create(member,output);
        return Result.success(output.getOutput().getTaskId());
    }

    @PostMapping(value = "/task")
    public Result task(@RequestHeader String deviceId,@CurrentUser Member member,String taskId){
        return Result.success(AliCommonUtils.getTask(taskId));
    }

}
