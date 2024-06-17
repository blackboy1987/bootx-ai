package com.bootx.controller.member;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.CategoryApp;
import com.bootx.entity.CategoryAppTask;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.CategoryAppService;
import com.bootx.service.CategoryAppTaskResultService;
import com.bootx.service.CategoryAppTaskService;
import com.bootx.util.JsonUtils;
import com.bootx.util.ali.TextUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
