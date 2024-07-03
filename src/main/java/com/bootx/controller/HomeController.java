package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.service.ImageTaskService;
import com.bootx.service.MemberService;
import com.bootx.service.SmsLogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        map.put("list",jdbcTemplate.queryForList("select name,icon,id from textapp where icon is not null limit 2000;"));
        apps.add(map);
        data.put("apps",apps);
        return Result.success(data);
    }



}
