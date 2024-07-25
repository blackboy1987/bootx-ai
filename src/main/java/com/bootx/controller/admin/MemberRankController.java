/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: lhHdzXmXjO7wzd/qxQiQgceFpRG9jm0H
 */
package com.bootx.controller.admin;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.MemberRank;
import com.bootx.service.MemberRankService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Controller - 会员等级
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController("adminMemberRankController")
@RequestMapping("/api/admin/member_rank")
public class MemberRankController extends BaseController {

	@Resource
	private MemberRankService memberRankService;

	/**
	 * 检查消费金额是否唯一
	 */
	@GetMapping("/check_amount")
	public @ResponseBody boolean checkAmount(Long id, BigDecimal amount) {
		return amount != null && memberRankService.amountUnique(id, amount);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(MemberRank memberRank) {
		if (!isValid(memberRank)) {
			return Result.error("参数校验失败");
		}
		memberRank.setMembers(null);
		memberRankService.save(memberRank);
		return Result.success();
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(MemberRank memberRank, Long id) {
		if (!isValid(memberRank)) {
			return Result.error("参数校验失败");
		}
		MemberRank pMemberRank = memberRankService.find(id);
		if (pMemberRank == null) {
			return Result.error("参数校验失败");
		}
		if (pMemberRank.getIsDefault()) {
			memberRank.setIsDefault(true);
		}
		memberRankService.update(memberRank, "members", "promotions");
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Result list(Pageable pageable) {
		return Result.success(memberRankService.findPage(pageable));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				MemberRank memberRank = memberRankService.find(id);
				if (memberRank != null && memberRank.getMembers() != null && !memberRank.getMembers().isEmpty()) {
					return Result.error("删除失败");
				}
			}
			long totalCount = memberRankService.count();
			if (ids.length >= totalCount) {
				return Result.error("删除失败");
			}
			memberRankService.delete(ids);
		}
		return Result.success();
	}

}