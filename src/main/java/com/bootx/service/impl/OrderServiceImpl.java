
package com.bootx.service.impl;

import com.bootx.dao.MemberDao;
import com.bootx.dao.OrderDao;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.entity.Order;
import com.bootx.service.OrderService;
import com.bootx.util.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author black
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Resource
	private OrderDao orderDao;

	@Resource
	private MemberDao memberDao;

	@Override
	public Order create(Member member, MemberRank memberRank) {
		Order order = new Order();
		order.setAmount(new BigDecimal("0.01"));
		order.setExpire(DateUtils.getNextMinute(30));
		order.setMember(member);
		order.setRewardPoint(0L);
		order.setSn(System.nanoTime()+"");
		if(memberRank!=null){
			order.setType(0);
			order.setMemberRank(memberRank);
			order.setMemo("会员升级："+memberRank.getName());
		}
		order.setStatus(0);
		return super.save(order);
	}

	@Override
	public Order findBySn(String outTradeNo) {
		return orderDao.find("sn",outTradeNo);
	}

	@Override
	public void finish(Order order) {
		if(order.getStatus()==2){
			Member member = order.getMember();
			MemberRank memberRank = order.getMemberRank();
			if(member!=null&&memberRank!=null){
				if(member.getMemberRank()==null){
					member.setMemberRank(memberRank);
					member.setMemberRankExpiredDate(DateUtils.getNextDay(memberRank.getDays()));
					memberDao.merge(member);
				}else if(member.getMemberRank()==memberRank){
					member.setMemberRankExpiredDate(DateUtils.getNextDay(member.getMemberRankExpiredDate(),memberRank.getDays()));
					memberDao.merge(member);
				}else{
					member.setMemberRank(memberRank);
					member.setMemberRankExpiredDate(DateUtils.getNextDay(memberRank.getDays()));
					memberDao.merge(member);
				}
			}
		}
	}
}