package com.bootx.controller;

import com.alibaba.fastjson.JSON;
import com.bootx.common.Result;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
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
@RestController("apiTextAppController")
@RequestMapping("/api/textApp")
public class TextAppController extends BaseController {

    @PostMapping("/category")
    private Result category(){
        String s = redisService.get("category");
        List<Map<String, Object>> maps = new ArrayList<>();
        try {
            maps = JsonUtils.toObject(s, new TypeReference<List<Map<String, Object>>>() {
            });
        }catch (Exception e){
            maps = jdbcTemplate.queryForList("select id,name from textappcategory");
            for (Map<String, Object> map : maps) {
                map.put("list",jdbcTemplate.queryForList("select id,name,memo from textapp where textAppCategory_id=?",map.get("id")));
            }
            redisService.set("category",JsonUtils.toJson(maps),30, TimeUnit.MINUTES);
        }

        return Result.success(maps);
    }
}
