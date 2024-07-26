
package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.entity.Order;

public interface OrderService extends BaseService<Order, Long> {

	Order create(Member member, MemberRank memberRank);

	Order findBySn(String outTradeNo);

	void finish(Order order);
}