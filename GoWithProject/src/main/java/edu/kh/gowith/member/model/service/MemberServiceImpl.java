package edu.kh.gowith.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;

	private final BCryptPasswordEncoder bcrypt;

	@Override
	public Member loginMember(Member member) {

		// 아직 이메일 일치 mapper 안만듬
		Member loginMember = mapper.loginMember(member.getMemberEmail());

		if (loginMember == null)
			return null;

		if (!bcrypt.matches(member.getMemberPw(), loginMember.getMemberPw()))
			return null;

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

}
