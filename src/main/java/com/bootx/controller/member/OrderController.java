package com.bootx.controller.member;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppTask;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.OrderService;
import com.bootx.service.TextAppService;
import com.bootx.service.TextAppTaskService;
import com.bootx.util.AiUtils;
import com.bootx.util.MessagePojo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/member/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    @PostMapping(value = "/list")
    public Result list(@CurrentUser Member member, HttpServletRequest request, Pageable pageable) {
        if(member==null){
            return Result.success(Collections.emptyList());
        }
        return Result.success(jdbcTemplate.queryForList("select sn,createdDate,amount,memo,type from orders where status=2 and member_id=? order by createdDate desc limit ?,?",member.getId(),(pageable.getPageNumber()-1)*pageable.getPageSize(),pageable.getPageSize()));
    }
}
