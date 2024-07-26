
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
}