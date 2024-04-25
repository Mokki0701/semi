package edu.kh.gowith.member.model.mapper;

import java.util.List;

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

	
}
