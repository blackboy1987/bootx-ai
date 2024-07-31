
package com.bootx.service.impl;

import com.bootx.common.Pageable;
import com.bootx.dao.MemberDao;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.service.MemberRankService;
import com.bootx.service.MemberService;
import com.bootx.util.JWTUtils;
import com.bootx.util.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

	@Resource
	private MemberDao memberDao;

	@Resource
	private MemberRankService memberRankService;

	@Override
	public Member getCurrent() {
		HttpServletRequest request = WebUtils.getRequest();
		if(request==null){
			return null;
		}
		try {
			String token = request.getHeader("token");
			String id = JWTUtils.getId(token);
            assert id != null;
            return super.find(Long.valueOf(id));
		}catch (Exception e){
			return null;
		}
	}

	@Override
	public void lock(Member member) {
		Long increment = redisService.increment(Member.FAILED_LOGIN_ATTEMPTS_CACHE_NAME + ":" + member.getId());
		if(increment>=5){
			member.setIsLocked(true);
			member.setLockDate(new Date());
			super.update(member);
		}
	}

	@Override
	public void unLock(Member member) {
		redisService.delete(Member.FAILED_LOGIN_ATTEMPTS_CACHE_NAME + ":" + member.getId());
	}

	@Override
	public List<Map<String, Object>> search(String keywords, Pageable pageable) {
		if(StringUtils.isBlank(keywords)){
			return Collections.emptyList();
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select ");
		stringBuffer.append("member.id, ");
		stringBuffer.append("member.avatar, ");
		stringBuffer.append("member.username, ");
		stringBuffer.append("member.remainPoint point, ");
		stringBuffer.append("memberrank.name rankName, ");
		stringBuffer.append("(select count(fan.id) from fan where member_id=1 and fan.fan_id=member.id) isConcern ");
		stringBuffer.append("from member,memberrank ");
		stringBuffer.append("where memberrank.id=member.memberRank_id ");
		stringBuffer.append("and member.username like ? ");
		stringBuffer.append(" order by member.createdDate desc ");
		stringBuffer.append("limit ?,?; ");
		return jdbcTemplate.queryForList(stringBuffer.toString(), "%"+keywords+"%",(pageable.getPageNumber()-1)*pageable.getPageSize(),pageable.getPageSize());
	}

	@Override
	public Member getCurrent(String token) {
		try {
			Claims claims = JWTUtils.parseToken(token);
			assert claims != null;
			String id = claims.getId();
			return super.find(Long.valueOf(id));
		}catch (Exception e){
			return null;
		}
	}

	@Override
	public Member create(String mobile,String deviceId) {
		Member member = findByMobile(mobile);
		if(member==null){
			member = new Member();
			member.setMemberRank(memberRankService.find(1L));
			member.setUsername(deviceId);
			member.setIsEnabled(true);
			member.setDeviceId(deviceId);
			member.setMobile(mobile);
			member.setIsLocked(false);
			return super.save(member);
		}
		return member;
	}

	@Override
	public void upgradeMemberRank(Member member, MemberRank memberRank) {
		MemberRank currentMemberRank = member.getMemberRank();
		if(currentMemberRank==null){
			// 直接升级
		}


	}

	public Member findByMobile(String mobile) {
		List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id from member where mobile=? limit 1", mobile);
		if(maps.isEmpty()){
			return null;
		}
		try {
			return find(Long.valueOf(maps.get(0).get("id")+""));
		}catch (Exception e){
			return null;
		}
	}
}