package edu.kh.gowith.member.model.service;

import edu.kh.gowith.member.model.dto.Member;

public interface MemberService {

	Member loginMember(Member member);

	// 빠른 로그인

	Member quickLogin(String memberEmail);

}
