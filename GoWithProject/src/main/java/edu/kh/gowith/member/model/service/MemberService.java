package edu.kh.gowith.member.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.MemberMenu;
import edu.kh.gowith.member.model.dto.Member;

public interface MemberService {

	// 로그인
	Member loginMember(Member member);

	// 작성한 글 개수
	int postCounter(int memberNo);
	
	// 댓글 수
	int commentCounter(int memberNo);
	
	// 좋아요 누른 갯수
	int memberLikeCount(int memberNo);
	
	// 빠른 로그인
	Member quickLogin(String memberEmail);

	// 좋아하는 하위 게시판
	List<BottomMenu> favorBoard(int memberNo);

	// 회원가입
	int signup(Member inputMember, String[] memberAddress);

	// 이메일 체크
	int checkEmail(String memberEmail);

	// 회원 가입 이메일 보내기
	String sendEmail(String string, String email);

	// 인증번호 확인
	int checkAuthKey(Map<String, Object> map);

	// 닉네임 체크
	int checkNickname(String memberNickname);

	String findId(Map<String, String> map);

	int resetPw(Map<String,String> paramMap);

	


	

	

	

}
