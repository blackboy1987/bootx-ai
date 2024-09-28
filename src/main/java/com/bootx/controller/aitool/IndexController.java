package com.bootx.controller.aitool;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.AiTool;
import com.bootx.entity.BaseEntity;
import com.bootx.service.AiToolService;
import com.bootx.service.impl.AiToolServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai_tool")
public class IndexController extends BaseController {

    @Resource
    private AiToolService aiToolService;

    @PostMapping("/category")
    public Result category(){
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select name from aitoolcategory where parent_id is null group by name");
        for (Map<String, Object> map : maps) {
            list.add(map);
            list.addAll(jdbcTemplate.queryForList("select id,CONCAT('    ',name) `name` from aitoolcategory where parent_id in (select id from aitoolcategory where parent_id is null and name=?) group by name",map.get("name")));
        }
        return Result.success(list);
    }
    @PostMapping("/data")
    public Result data(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,name,icon,memo from aitool");
        return Result.success(list);
    }
    @PostMapping("/detail")
    @JsonView(BaseEntity.ViewView.class)
    public Result detail(Long id){
        AiTool aiTool = aiToolService.find(id);
        return Result.success(aiTool);
    }

}
