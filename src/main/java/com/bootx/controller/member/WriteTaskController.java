package com.bootx.controller.member;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.TextAppTask;
import com.bootx.security.CurrentUser;
import com.bootx.service.TextAppTaskService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/member/writeTask")
public class WriteTaskController extends BaseController {

    @Resource
    private TextAppTaskService textAppTaskService;

    @PostMapping(value = "/save")
    public Result save(@CurrentUser Member member, String taskId, String content, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            System.out.println(headerName+":"+request.getHeader(headerName));
        }
        TextAppTask byTaskId = textAppTaskService.findByTaskId(taskId);
        if(byTaskId!=null){
            byTaskId.setResult(content);
            byTaskId.setStatus(2);
            textAppTaskService.update(byTaskId);
        }
        return Result.success();
    }

    /**
     * 历史记录
     * @param member
     * @param textAppId
     * @param request
     * @return
     */
    @PostMapping(value = "/list")
    public Result list(@CurrentUser Member member, Long textAppId, HttpServletRequest request, Pageable pageable) {
        System.out.println(pageable.getPageNumber());
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,DATE_FORMAT(createdDate,'%Y-%m-%d %H:%i') createdDate,concat(LEFT(result,10),'...') title from textapptask where 1=1 order by createdDate desc limit ?,?",(pageable.getPageNumber()-1)*pageable.getPageSize(),pageable.getPageSize());
        System.out.println(list.size());
        return Result.success(list);
    }
    @PostMapping(value = "/view")
    public Result view(@CurrentUser Member member, Long textAppTaskId, HttpServletRequest request) {
        try {
            return Result.success(textAppTaskService.find(textAppTaskId).getResult());
        }catch (Exception e){
            return Result.error("请求失败");
        }
    }
}
