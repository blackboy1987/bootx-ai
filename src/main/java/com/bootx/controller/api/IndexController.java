package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.util.AiUtils;
import com.bootx.util.AiVlUtils;
import com.bootx.util.MessagePojo;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author black
 */
@RestController
@RequestMapping("/api")
public class IndexController extends BaseController {

    @GetMapping(value = "/message",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessagePojo> message(String content, HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            System.out.println(s+":"+request.getHeader(s));
        }


        List<MessagePojo> list = new ArrayList<>();
        final Boolean[] isTop = {false};
        AtomicReference<Integer> index = new AtomicReference<>(0);
        new Thread(()->{
            AiUtils.message(content, message->{
                System.out.println(new Date());
                if (!isTop[0] && StringUtils.isNotBlank(message.getRequestId())){
                    list.add(message);
                    isTop[0] = StringUtils.equalsIgnoreCase(message.getFinishReason(),"stop");
                }
            });
        }).start();

        return Flux.interval(Duration.ofMillis(1000)).map(sequence -> {
            // 如果没有结束
            if (index.get()<list.size() ){
                System.out.println(index.get());
                try {
                    return list.get(index.getAndSet(index.get() + 1));
                }catch (Exception e){
                    index.set(index.get()-1);
                    System.out.println(index.get());
                    // 没有结束还有消息，但是list里面的消息已经获取完毕了
                    return new MessagePojo();
                }
            }else {
                return MessagePojo.stop();
            }
        });
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

}
