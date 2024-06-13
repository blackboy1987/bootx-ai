
package com.bootx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * Entity - 会员
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class Member extends User {

	/**
	 * 手机
	 */
	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^1[3|4|5|6|7|8|9]\\d{9}$")
	@Column(nullable = false)
	private String mobile;

	/**
	 * 积分
	 */
	private Long point;


	/**
	 * 会员等级
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private MemberRank memberRank;

	/**
	 * 获取手机
	 * 
	 * @return 手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * 
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取积分
	 * 
	 * @return 积分
	 */
	public Long getPoint() {
		return point;
	}

	/**
	 * 设置积分
	 * 
	 * @param point
	 *            积分
	 */
	public void setPoint(Long point) {
		this.point = point;
	}

	/**
	 * 获取会员等级
	 * 
	 * @return 会员等级
	 */
	public MemberRank getMemberRank() {
		return memberRank;
	}

	/**
	 * 设置会员等级
	 * 
	 * @param memberRank
	 *            会员等级
	 */
	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setMobile(StringUtils.lowerCase(getMobile()));
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		setMobile(StringUtils.lowerCase(getMobile()));
	}

}