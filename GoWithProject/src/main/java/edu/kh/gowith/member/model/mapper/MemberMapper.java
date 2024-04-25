package edu.kh.gowith.member.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.MemberMenu;
import edu.kh.gowith.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	// 로그인
	Member loginMember(String memberEmail);

	// 작성한 글 개수
	int postCounter(int memberNo);

	// 좋아하는 하위 게시판
	List<BottomMenu> favorBoard(int memberNo);

	// 회원가입
	int signup(Member inputMember);

	// 이메일 체크
	int checkEmail(String memberEmail);

	
	// --------------- 회원가입 기능 --------------- //
	// 이메일인증번호 기능 //
	int updateAuthKey(Map<String, String> map);
	int insertAuthKey(Map<String, String> map);
	int checkAuthKey(Map<String, Object> map);
	
	// 닉네임 중복체크
	int checkNickname(String memberNickname);

	
}
