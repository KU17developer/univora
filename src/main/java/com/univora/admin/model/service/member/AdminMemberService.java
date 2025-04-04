package com.univora.admin.model.service.member;

import static com.univora.common.SqlSessionTemplate.getSession;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.univora.admin.model.dao.board.AdminMemberDao;
import com.univora.admin.model.dto.Member;

public class AdminMemberService {

	AdminMemberDao memberDao = new AdminMemberDao();
	
	
	public List<Member> selectMemberAll(Map<String, Integer> param) {
		
		SqlSession session= getSession();
		
		List<Member> m = memberDao.selectMemberAll(session,param);
		session.close();
		
		return m;
		
	}

	public int selectMemberCount() {
		SqlSession session=getSession();
		int count=memberDao.selectMemberCount(session);
		session.close();
		return count;
	}

	public Member selectMemberById(String memberId) {
		SqlSession session = getSession();
		Member searchMember = memberDao.selectMemberByid(session, memberId);
		return searchMember;
	}

	public int updateMemberRole(Member memberInfo) {
		SqlSession session = getSession();
		
		int result= memberDao.updateMemberRole(session, memberInfo);
		
		if(result>0) {
			session.commit();
		}else {
			session.rollback();
		}
		session.close();
		return result;
		
	}
}
