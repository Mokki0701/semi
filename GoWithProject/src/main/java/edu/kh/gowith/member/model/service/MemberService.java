package edu.kh.gowith.member.model.service;

import java.util.List;

import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.MemberMenu;
import edu.kh.gowith.member.model.dto.Member;

public interface MemberService {

	// 로그인
	Member loginMember(Member member);

	// 작성한 글 개수
	int postCounter(int memberNo);
	
	// 빠른 로그인
	Member quickLogin(String memberEmail);

	// 좋아하는 하위 게시판
	List<BottomMenu> favorBoard(int memberNo);

	// 회원가입
	int signup(Member inputMember, String[] memberAddress);

	

}
