package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.internal.StringUtil;
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
        String s = redisService.get("category1234");
        List<Map<String, Object>> maps = new ArrayList<>();
        try {
            maps = JsonUtils.toObject(s, new TypeReference<List<Map<String, Object>>>() {
            });
            if(maps.isEmpty()){
                maps = jdbcTemplate.queryForList("select id,name from (select id,name,(select count(textapp.id) from textapp where textAppCategory_id=textappcategory.id) `count` from textappcategory) AS X where X.count>0 order by X.id;");
                for (Map<String, Object> map : maps) {
                    map.put("list",jdbcTemplate.queryForList("select id,name,memo from textapp where textAppCategory_id=?",map.get("id")));
                }
                redisService.set("category",JsonUtils.toJson(maps),30, TimeUnit.MINUTES);
            }
        }catch (Exception e){
            maps = jdbcTemplate.queryForList("select id,name from (select id,name,(select count(textapp.id) from textapp where textAppCategory_id=textappcategory.id) `count` from textappcategory) AS X where X.count>0 order by X.id;");
            for (Map<String, Object> map : maps) {
                map.put("list",jdbcTemplate.queryForList("select id,name,memo from textapp where textAppCategory_id=?",map.get("id")));
            }
            redisService.set("category",JsonUtils.toJson(maps),30, TimeUnit.MINUTES);
        }

        return Result.success(maps);
    }
    @PostMapping("/detail")
    private Result detail(Long appId){
        Map<String, Object> map = jdbcTemplate.queryForMap("select id,name,formList from textapp where id=?", appId);
        List<Map<String,Object>> formList = new ArrayList<>();
        if(map.get("formList")!=null&&StringUtils.isNoneBlank((map.get("formList")+""))){
            formList = JsonUtils.toObject(map.get("formList") + "", new TypeReference<List<Map<String, Object>>>() {
            });
        }else{
            Map<String,Object> formItem = new HashMap<>();
            formItem.put("label","选择语气");
            formItem.put("name","topic");
            formItem.put("placeholder","18岁女孩的生日聚会");
            formItem.put("isRequired",true);
            formItem.put("type","select");
            formItem.put("min",1);
            formItem.put("max",1);
            formItem.put("value","");
            formItem.put("options","有说服力,正式得体,专业,幽默,热情");
            formList.add(formItem);

            Map<String,Object> formItem1 = new HashMap<>();
            formItem1.put("label","视频标题");
            formItem1.put("placeholder","如何成为一名富豪");
            formItem1.put("name","title");
            formItem1.put("isRequired",true);
            formItem1.put("type","input");
            formItem1.put("maxLines",10);
            formItem1.put("minLines",10);
            formItem1.put("min",10);
            formItem1.put("max",500);
            formItem1.put("value","");
            formItem1.put("showClear",false);
            formList.add(formItem1);

            if(appId==566){
                jdbcTemplate.update("update textapp set formList=? where id=?",JsonUtils.toJson(formList),appId);
            }
        }
        map.put("formList",formList);

        return Result.success(map);
    }

    @PostMapping("/search")
    private Result search(String keywords){
        System.out.println(keywords);
        return Result.success(jdbcTemplate.queryForList("select id,name,memo,icon from textApp where name like ?","%"+keywords+"%"));
    }

    @PostMapping("/office")
    private Result office(){
        return Result.success(jdbcTemplate.queryForList("select id,name,memo,icon from textapp where textAppCategory_id in (select id from textappcategory where name='办公');"));
    }
}
