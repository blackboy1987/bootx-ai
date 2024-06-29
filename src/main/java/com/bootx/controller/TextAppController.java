package com.bootx.controller;

import com.bootx.common.Result;
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
@RestController("apiTextAppController")
@RequestMapping("/api/textApp")
public class TextAppController extends BaseController {

    @PostMapping("/category")
    private Result category(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,name from textappcategory");
        for (Map<String, Object> map : maps) {
            map.put("list",jdbcTemplate.queryForList("select id,name,memo from textapp where textAppCategory_id=?",map.get("id")));
        }
        return Result.success(maps);
    }
}
