package edu.kh.gowith.member.model.service;

import edu.kh.gowith.member.model.dto.Member;

public interface MemberService {

	Member loginMember(Member member);

	Member quickLogin(Member member);

}
