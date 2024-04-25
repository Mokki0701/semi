package edu.kh.gowith.member.model.service;

import java.util.List;

//import org.springframework.mail.javamail.JavaMailSender;
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
//	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;

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

		System.out.println("로그인 멤버 :" + loginMember);

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

//	// 회원가입 이메일 발송
//	@Override
//	public String sendEmail(String htmlName, String email) {
//		
//		String authKey = createAuthKey();
//		
//		try {
//			
//			String subject = null;
//			switch(htmlName) {
//			case "authMailSend" : subject = "GoWith 회원 가입 인증번호입니다";
//				break;
//			}
//			
//			// 인증 메일 보내기
//			MimeMessage mimeMessage = mailSender.createMimeMessage();
//			
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			
//			helper.setTo(email);
//			helper.setSubject(subject);
//			helper.setText(loadHtml(authKey, htmlName), true);
//		}
//		
//		public String loadHtml(String authKey, String htmlName) {
//			
//			// org.thymeleaf.context 선택!!!!!
//			// forward하는 용도가 아닌 자바에서 쓰고싶을 때 쓰는 thymeleaf 객체
//			Context context = new Context();
//			
//			// 타임리프가 적용된 html에서 사용할 값
//			context.setVariable("authKey", authKey);
//			
//			// templates/email 폴더에서 htmlName과 같은 .html 파일 내용을 읽어와
//			// String으로 변환을 시킨다
//			return templateEngine.process("email/" + htmlName, context);
//			
//		}
//		
//		
//		return null;
//	}
//	
//	
//	// 이메일 체크
//	@Override
//	public int checkEmail(String memberEmail) {
//		return mapper.checkEmail(memberEmail);
//	}
//	
//	
}
