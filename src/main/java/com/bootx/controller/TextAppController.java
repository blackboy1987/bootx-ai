package com.bootx.controller;

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
    @PostMapping("/detail")
    private Result detail(Long appId){
        Map<String, Object> map = jdbcTemplate.queryForMap("select id,name from textapp where id=?", appId);
        List<Map<String,Object>> formList = new ArrayList<>();
        Map<String,Object> formItem = new HashMap<>();
        formItem.put("label","选择语气");
        formItem.put("name","type");
        formItem.put("isRequired",true);
        formItem.put("type","select");
        formItem.put("min",1);
        formItem.put("max",1);
        formItem.put("value","");
        formItem.put("options","有说服力,正式得体,专业,幽默,热情");
        formList.add(formItem);
        map.put("formList",formList);

        Map<String,Object> formItem1 = new HashMap<>();
        formItem1.put("label","想写些什么");
        formItem1.put("name","content");
        formItem1.put("isRequired",true);
        formItem1.put("type","input");
        formItem.put("maxLines",10);
        formItem.put("minLines",10);
        formItem1.put("min",10);
        formItem1.put("max",500);
        formItem1.put("value","");
        formItem1.put("showClear",true);
        formList.add(formItem1);

        map.put("formList",formList);
        if(appId==513){
            jdbcTemplate.update("update textapp set formList=? where id=?",JsonUtils.toJson(formList),appId);
        }
        return Result.success(map);
    }
}
