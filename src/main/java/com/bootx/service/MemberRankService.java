
package com.bootx.service;

import com.bootx.entity.MemberRank;

import java.math.BigDecimal;

/**
 * Service - 会员等级
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MemberRankService extends BaseService<MemberRank, Long> {

	/**
	 * 判断消费金额是否存在
	 * 
	 * @param point
	 *            消费金额
	 * @return 消费金额是否存在
	 */
	boolean pointExists(Long point);

	/**
	 * 判断消费金额是否唯一
	 * 
	 * @param id
	 *            ID
	 * @param point
	 *            消费金额
	 * @return 消费金额是否唯一
	 */
	boolean pointUnique(Long id, Long point);

	/**
	 * 查找默认会员等级
	 * 
	 * @return 默认会员等级，若不存在则返回null
	 */
	MemberRank findDefault();

	/**
	 * 根据消费金额查找符合此条件的最高会员等级
	 * 
	 * @param point
	 *            消费金额
	 * @return 会员等级，不包含特殊会员等级，若不存在则返回null
	 */
	MemberRank findByPoint(Long point);

}