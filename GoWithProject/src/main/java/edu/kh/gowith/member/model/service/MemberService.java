package edu.kh.gowith.member.model.service;

import java.util.List;

import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.member.model.dto.MemberMenu2;

public interface MemberService {

	// 로그인
	Member loginMember(Member member);

	// 작성한 글 개수
	int postCounter(int memberNo);
	
	// 빠른 로그인
	Member quickLogin(String memberEmail);

	// 좋아하는 하위 게시판
	List<MemberMenu2> favorBoard(int memberNo);

	

}
