package edu.kh.gowith.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.MemberMenu;
import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.member.model.mapper.MemberMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;
	private final BCryptPasswordEncoder bcrypt;
	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;

	@Override
	public Member loginMember(Member member) {

		// 암호화
		// bcrypt.encode(문자열) : 문자열을 암호화하여 반환해줌
		String bcryptPassword = bcrypt.encode(member.getMemberPw());
		
		
		member.setMemberNo(mapper.getMemberNo(member.getMemberEmail()));
		
		// 이메일이 일차하며, 탈퇴하지 않은 회원 조회
		Member loginMember = mapper.loginMember2(member);

		if (loginMember == null)
			return null;

		// 비밀번호 평문, 암호화 확인
		if (!bcrypt.matches(member.getMemberPw(), loginMember.getMemberPw()))
			return null;

		System.out.println("로그인 멤버 :" + loginMember);

		// 임시 비밀번호
//		if (!member.getMemberPw().equals(loginMember.getMemberPw())) {
//			return null;
//		}

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
	
	// 작성한 댓글 갯수
	@Override
	public int commentCounter(int memberNo) {

		int result = mapper.commentCounter(memberNo);
		
		return result;
	}

	// 좋아하는 하위 게시판
	@Override
	public List<BottomMenu> favorBoard(int memberNo) {

		return mapper.favorBoard(memberNo);
	}

	// 회원가입
	@Override
	public int signup(Member inputMember, String[] memberAddress) {

		if (!inputMember.getMemberAddress().equals(",,")) {
			String address = String.join("^^^", memberAddress);
			inputMember.setMemberAddress(address);
		} else {
			inputMember.setMemberAddress(null);
		}

		// 비밀번호 암호화
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);

		return mapper.signup(inputMember);

	}

	// 회원가입 이메일 발송
	@Override
	public String sendEmail(String htmlName, String email) {

		String authKey = createAuthKey();

		try {

			String subject = null;

			switch (htmlName) {
			case "authMailSend":
				subject = "GoWith 회원 가입 인증번호입니다";
				break;
			}

			// 인증 메일 보내기
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(loadHtml(authKey, htmlName), true);
			// 로고
//			helper.addInline("logo", new ClassPathResource("static/images/logo.jpg"));

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Map<String, String> map = new HashMap<>();
		map.put("authKey", authKey);
		map.put("email", email);

		int result = mapper.updateAuthKey(map);

		if (result == 0) {
			result = mapper.insertAuthKey(map);
		}

		if (result == 0) {
			return null;
		}

		return authKey;
	}

	//
	public String loadHtml(String authKey, String htmlName) {

		Context context = new Context();

		context.setVariable("authKey", authKey);

		return templateEngine.process("email/" + htmlName, context);

	}

	// 인증번호 생성
	public String createAuthKey() {
		String key = "";
		for (int i = 0; i < 6; i++) {

			int sel1 = (int) (Math.random() * 3); // 0:숫자 / 1,2:영어

			if (sel1 == 0) {

				int num = (int) (Math.random() * 10); // 0~9
				key += num;

			} else {

				char ch = (char) (Math.random() * 26 + 65); // A~Z

				int sel2 = (int) (Math.random() * 2); // 0:소문자 / 1:대문자

				if (sel2 == 0) {
					ch = (char) (ch + ('a' - 'A')); // 대문자로 변경
				}

				key += ch;
			}

		}
		return key;
	}

	@Override
	public int checkAuthKey(Map<String, Object> map) {
		
		return mapper.checkAuthKey(map);
	}
	
	// 이메일 체크
	@Override
	public int checkEmail(String memberEmail) {
		return mapper.checkEmail(memberEmail);
	}
	
	// 닉네임 체크
	@Override
	public int checkNickname(String memberNickname) {
		
		return mapper.checkNickname(memberNickname);
	}
	
	
	@Override
	public String findId(Map<String, String> map) {
		String email = mapper.fidId(map);
		
		if(email == null) {
			return null;
		}
		
		return email;
	}
	
	@Override
	public int resetPw(Map<String, String> paramMap) {
		
		String pw = bcrypt.encode(paramMap.get("password"));
		
		paramMap.put("newPw", pw);
		
		return mapper.resetPw(paramMap);
	}
	
	
	
	
	
	
	
	
	
	

}
