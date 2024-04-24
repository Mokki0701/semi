package edu.kh.gowith.member.model.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.member.model.dto.MemberMenu2;
import edu.kh.gowith.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;

	private final BCryptPasswordEncoder bcrypt;

	@Override
	public Member loginMember(Member member) {

		// 암호화
		// bcrypt.encode(문자열) : 문자열을 암호화하여 반환해줌
//		String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
		
		// 이메일이 일차하며, 탈퇴하지 않은 회원 조회
		Member loginMember = mapper.loginMember(member.getMemberEmail());
		

		if (loginMember == null)
			return null;

//		비밀번호 평문, 암호화 확인
//		if (!bcrypt.matches(member.getMemberPw(), loginMember.getMemberPw()))
//			return null;
		
		System.out.println("로그인 멤버 :" + loginMember) ; 
		
		// 임시 비밀번호
		if (!member.getMemberPw().equals(loginMember.getMemberPw())) {
		    return null;
		}

		// 로그인 결과에서 비밀번호 제거
		loginMember.setMemberPw(null);

		return loginMember;
	}

	// 빠른 로그인
	@Override
	public Member quickLogin(String memberEmail) {

		Member loginMember = mapper.loginMember(memberEmail);

		// 탈퇴 또는 없는 경우
		if (loginMember == null)
		return null;

		// 조회된 비밀번호 null로 변경
		loginMember.setMemberPw(null);
		
		return loginMember;

	}
	
	// 작성한 글 개수
	@Override
	public int postCounter(int memberNo) {
		
		int result = mapper.postCounter(memberNo);
		
		return result;
	}

	// 좋아하는 하위 게시판
	@Override
	public List<MemberMenu2> favorBoard(int memberNo) {
		
		
		return mapper.favorBoard(memberNo);
	}
	
}
