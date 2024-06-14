package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController extends BaseController {

    @PostMapping(value = "/list")
    public Result message(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,name from category where grade=0;");

        for (Map<String, Object> map : maps) {
            map.put("apps",jdbcTemplate.queryForList("select categoryapp.id, categoryapp.thumb, title,categoryapp.memo from categoryapp,category where categoryapp.category_id=category.id  and (category.id=? or category.treePath like ?);",map.get("Id"),"%,"+map.get("id")+",%"));
        }
        return Result.success(maps);
    }

}
