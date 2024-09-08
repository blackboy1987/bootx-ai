
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.util.DateUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * @author black
 */
@RestController
@RequestMapping("/api/member/coupon_code")
public class CouponCodeController extends BaseController {

	@Resource
	private MemberService memberService;


	@PostMapping
	public Result index(@CurrentUser Member member, HttpServletRequest request,String code) {
		if(member==null){
			return Result.error("请先登录");
		}
		if(member.getMemberRankExpiredDate()==null){
			member.setMemberRankExpiredDate(new Date());
		}
		member.setMemberRankExpiredDate(DateUtils.getNextDay(member.getMemberRankExpiredDate(),100));
		memberService.update(member);
		return Result.success();
	}

}