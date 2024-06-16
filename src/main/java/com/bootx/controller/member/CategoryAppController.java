package com.bootx.controller.member;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.*;
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
@RequestMapping("/api/member/categoryApp")
public class CategoryAppController extends BaseController {

    @Resource
    private CategoryAppService categoryAppService;

    @Resource
    private CategoryAppTaskService categoryAppTaskService;

    @Resource
    private CategoryAppTaskResultService categoryAppTaskResultService;

    @PostMapping(value = "/config")
    @JsonView(BaseEntity.ViewView.class)
    public Result config(Long id) {
        return Result.success(categoryAppService.find(id));
    }

    @PostMapping(value = "/write")
    public Result write(Long categoryAppId, String params, @CurrentUser Member member) throws NoApiKeyException, InputRequiredException, InterruptedException {
        System.out.println(params);
        CategoryApp categoryApp = categoryAppService.find(categoryAppId);
        CategoryAppTask categoryAppTask = categoryAppTaskService.create(categoryApp, member, params);
        StringBuilder content = new StringBuilder(categoryApp.getTitle()+"。");
        Map<String, Object> map = JsonUtils.toObject(categoryAppTask.getParams(), new TypeReference<Map<String, Object>>() {
        });
        for (String key : map.keySet()) {
            if (
                    !StringUtils.equalsAnyIgnoreCase("categoryAppId", key)
                            && !StringUtils.equalsAnyIgnoreCase("categoryAppName", key)) {
                content.append(key).append("是").append(map.get(key)).append("。");
            }

        }
        TextUtils.streamCallWithCallback(content.toString(),
                messagePojo -> {
                    categoryAppTaskService.start(categoryAppTask);
                    categoryAppTaskResultService.create(categoryAppTask, messagePojo);
                },
                err -> categoryAppTaskService.error(categoryAppTask),
                code -> categoryAppTaskService.complete(categoryAppTask)
        );


        return Result.success();
    }

}
