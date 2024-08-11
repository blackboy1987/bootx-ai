package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.ImageTaskService;
import com.bootx.service.MemberService;
import com.bootx.service.SmsLogService;
import com.bootx.util.*;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;
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


}
