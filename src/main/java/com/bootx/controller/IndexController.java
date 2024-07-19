package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;
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
@RestController("apiIndexController")
@RequestMapping("/api")
public class IndexController extends BaseController {

    /**
     * 检测是否需要升级
     * @param member
     * @param request
     * @param versionName
     * @return
     */
    @PostMapping("/check")
    private Result check(@CurrentUser Member member, HttpServletRequest request,String versionName){
        return Result.success(1,"http://file.igomall.xin/aishangai_1.1.2.apk");
    }

    @PostMapping("/adviser")
    private Result adviser(@CurrentUser Member member, HttpServletRequest request,String version){
        return Result.success(jdbcTemplate.queryForList("select id,icon,name,memo from textapp where type=1"));
    }

}
