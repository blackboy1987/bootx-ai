package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.AdLog;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.AdLogService;
import com.bootx.service.TextAppService;
import com.bootx.util.IPUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("apiIndexController")
@RequestMapping("/api")
public class IndexController extends BaseController {

    @Resource
    private AdLogService adLogService;
    /**
     * 检测是否需要升级
     * @param member
     * @param request
     * @param versionName
     * @return
     */
    @PostMapping("/check")
    private Result check(@CurrentUser Member member, HttpServletRequest request,String versionName){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select url from appversion where versionCode>? limit 1;",versionName);
        if(maps.isEmpty()){
            return Result.success();
        }
        return Result.success(1,maps.getFirst().get("url"));
    }

    @PostMapping("/adviser")
    private Result adviser(@CurrentUser Member member, HttpServletRequest request,String version){
        return Result.success(jdbcTemplate.queryForList("select id,icon,name,memo from textapp where type=1"));
    }

    @GetMapping("/setting")
    private Map<String,Object> config(HttpServletRequest request){
        Map<String,Object> data = new HashMap<>();
        data.put("umengAndroid","6665a83ccac2a664de45b89b");
        data.put("umengIos","6665a86a940d5a4c4967f66f");
        data.put("umengName","测试");
        // 广告配置
        try {
            data.put("adConfig",jdbcTemplate.queryForMap("select adId,bannerId,dynamicExpressAdId,fullScreenVideoAdId,interactionAdId,mediaId,rewardVideoAdId,splashAdId from adconfig where isOpen=true order by id desc limit 1;"));
        }catch (Exception e){
            data.put("adConfig", Collections.emptyMap());
        }
        return data;
    }

    @PostMapping("/memberRank")
    private Result memberRank(HttpServletRequest request){
        List<Map<String, Object>> data = jdbcTemplate.queryForList("select id,name,isRecommend,memo,originalPrice,price,days from memberrank where isDefault=false order by originalPrice asc ;");
        return Result.success(data);
    }

    @PostMapping("/adLog")
    private Result adLog(AdLog adLog,@CurrentUser Member member,HttpServletRequest request){
        adLog.setMember(member);
        adLog.setIp(IPUtils.getIpAddr(request));
        adLogService.save(adLog);
        return Result.success();
    }
}
