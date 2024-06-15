package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.CategoryApp;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.CategoryAppService;
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
@RequestMapping("/api/member/categoryApp")
public class CategoryAppController extends BaseController {

    @Resource
    private CategoryAppService categoryAppService;

    @Resource
    private CategoryAppTaskService categoryAppTaskService;

    @PostMapping(value = "/config")
    @JsonView(BaseEntity.ViewView.class)
    public Result config(Long id){
        return Result.success(categoryAppService.find(id));
    }

    @PostMapping(value = "/write")
    public Result write(Long categoryAppId, String params, @CurrentUser Member member){
        CategoryApp categoryApp = categoryAppService.find(categoryAppId);
        categoryAppTaskService.create(categoryApp,member,params);
        return Result.success();
    }

}
