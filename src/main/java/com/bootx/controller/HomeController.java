package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.service.ImageTaskService;
import com.bootx.service.MemberService;
import com.bootx.service.SmsLogService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@RestController("apiHomeController")
@RequestMapping("/api/home")
public class HomeController extends BaseController {

    @PostMapping
    private Result index(){
        Map<String,Object> data = new HashMap<>();
        List<Map<String,Object>> apps = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("title","热门创作");
        List<Map<String,Object>> list = new ArrayList<>();
        String s = redisService.get("home_index2");
        try {
            list = JsonUtils.toObject(s, new TypeReference<List<Map<String, Object>>>() {
            });
            if(list.isEmpty()){
                list = jdbcTemplate.queryForList("select name,icon,id from textapp where icon is not null and isRecommend=true and type=0 limit 15;");
                redisService.set("home_index",JsonUtils.toJson(list),1, TimeUnit.DAYS);
                map.put("list",list);
            }
        }catch (Exception e){
            list = jdbcTemplate.queryForList("select name,icon,id from textapp where icon is not null and isRecommend=true and type=0 limit 15;");
            redisService.set("home_index",JsonUtils.toJson(list),1, TimeUnit.DAYS);
            map.put("list",list);
        }
        apps.add(map);
        data.put("apps",apps);
        return Result.success(data);
    }



}
