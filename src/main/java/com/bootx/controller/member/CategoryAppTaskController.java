package com.bootx.controller.member;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.service.CategoryAppTaskService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/member/categoryAppTask")
public class CategoryAppTaskController extends BaseController {

    @Resource
    private CategoryAppTaskService categoryAppTaskService;

    @PostMapping(value = "/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Long id, Pageable pageable) {
        return Result.success(categoryAppTaskService.findPage(pageable).getContent());
    }
    @PostMapping(value = "/detail")
    public Result detail(String taskId) {
        return Result.success(jdbcTemplate.queryForMap("select params,status,taskBeginDate,taskEndDate,categoryapp.title,c.content,c.outputTokens,c.inputTokens,c.totalTokens from categoryapptask left join categoryapptaskresult c on categoryapptask.id = c.categoryAppTask_id,categoryapp  where categoryapp.id=categoryapptask.categoryApp_id and taskId=?",taskId));
    }
}
