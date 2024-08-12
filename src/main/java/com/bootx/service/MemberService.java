
package com.bootx.service;

import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface MemberService extends BaseService<Member, Long> {


	Member getCurrent();

	void lock(Member member);

	void unLock(Member member);

    List<Map<String,Object>> search(String keywords, Pageable pageable);

	Member getCurrent(String token);

	Member findByMobile(String deviceId);

    Member create(String mobile, String deviceId);

    void upgradeMemberRank(Member member, MemberRank memberRank);

	/**
	 * 判断用户名是否存在
	 *
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找会员
	 *
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);

	/**
	 * 判断E-mail是否唯一
	 *
	 * @param id
	 *            ID
	 * @param username
	 *            用户名(忽略大小写)
	 * @return username是否唯一
	 */
	boolean usernameUnique(Long id, String username);
}