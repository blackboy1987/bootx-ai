package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.TextAppTask;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.TextAppService;
import com.bootx.service.TextAppTaskService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;

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
    private TextAppService textAppService;

    @Resource
    private TextAppTaskService textAppTaskService;
    @Resource
    private MemberService memberService;

    @PostMapping(value = "/save")
    public Result write(@CurrentUser Member member, String taskId, String content, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            System.out.println(headerName+":"+request.getHeader(headerName));
        }
        TextAppTask byTaskId = textAppTaskService.findByTaskId(taskId);
        if(byTaskId!=null){
            byTaskId.setResult(content);
            textAppTaskService.update(byTaskId);
        }
        return Result.success();
    }
    @PostMapping(value = "/list")
    public Result list(@CurrentUser Member member, Long textAppId, HttpServletRequest request) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,DATE_FORMAT(createdDate,'%Y-%m-%d %H:%i') createdDate,concat(LEFT(result,10),'...') title from textapptask where result is not null and result !='' order by createdDate desc ");
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
