package edu.kh.gowith.member.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.MemberMenu;
import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({ "loginMember", "postCounter", "favorBoard", "commentCounter" })
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
@Slf4j
public class MemberController {

	private final MemberService service;

	// 회원가입 페이지로 이동

	@GetMapping("join")
	public String toJoin() {
		return "common/join";
	}

	// 빠른 로그인
	@GetMapping("quickLogin")
	public String quikLogin(@RequestParam("memberEmail") String memberEmail, Model model, RedirectAttributes ra) {

		Member loginMember = service.quickLogin(memberEmail);

		if (loginMember == null) {
			ra.addFlashAttribute("message", "해당 이메일 회원이 존재하지 않습니다");

			// 빠른 로그인 성공 시
		} else {
			int postCounter = service.postCounter(loginMember.getMemberNo());
			int commentCounter = service.commentCounter(loginMember.getMemberNo());

			List<BottomMenu> favorBoard = service.favorBoard(loginMember.getMemberNo());

			StringBuilder sb = new StringBuilder();
			for (BottomMenu menu : favorBoard) {
				sb.append(menu.toString());
				sb.append(", "); // 요소 사이에 구분자 추가 (옵션)
			}
			String favorBoardAsString = sb.toString();

			// 콘솔로 확인
			System.out.println("favorBoard: " + favorBoardAsString);

			model.addAttribute("loginMember", loginMember);
			model.addAttribute("postCounter", postCounter);
			model.addAttribute("commentCounter", commentCounter);
			model.addAttribute("favorBoard", favorBoard);

		}

		return "redirect:/";
	}

	@GetMapping("login")
	public String login() {

		return "common/login";
	}

	// 일반 로그인
	@PostMapping("login")
	public String loginMember(@ModelAttribute Member member, RedirectAttributes ra, Model model,
			@RequestParam(value = "saveId", required = false) String saveId, HttpServletResponse resp) {

		Member loginMember = service.loginMember(member);

		if (loginMember == null) { // 로그인 실패
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		if (loginMember != null) { // 로그인 성공
			int postCounter = service.postCounter(loginMember.getMemberNo());
			model.addAttribute("loginMember", loginMember);
			int postCounter2 = service.postCounter(loginMember.getMemberNo());
			int commentCounter = service.commentCounter(loginMember.getMemberNo());

			// 아이디 저장용 쿠키
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			cookie.setPath("/");

			if (saveId != null) {
				cookie.setMaxAge(60 * 30); // 30분 유지
			} else {
				cookie.setMaxAge(0);
			}

			resp.addCookie(cookie);

			model.addAttribute("postCounter", postCounter2);
			model.addAttribute("commentCounter", commentCounter);
		}

		return "redirect:/";
	}

	// 로그아웃
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}

	// 회원가입
	@PostMapping("join")
	public String signUp(Member inputMember, @RequestParam("memberAddress") String[] memberAddress,
			RedirectAttributes ra) {

		int result = service.signup(inputMember, memberAddress);
		String path = null;
		String message = null;
		if (result > 0) {
			message = "😀😁😊 " + inputMember.getMemberNickname() + " 님의 가입을 환영합니다 😀😁😊";
			path = "/";
		} else {
			message = "회원 가입 실패";
			path = "join";
		}

		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}

	/**
	 * 이메일 중복 검사
	 * 
	 * @param memberEmail
	 * @return 중복 1, 아니면 0
	 */
	@ResponseBody // 응답 본문(요청한 fetch())으로 돌려보냄
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {

		return service.checkEmail(memberEmail);
	}

	// 이메일 보내기
	@ResponseBody
	@PostMapping("authMailSend")
	public int signup(@RequestBody String email) {
		String authKey = service.sendEmail("authMailSend", email);

		if (authKey != null) {
			return 1;
		}
		return 0;
	}

	/**
	 * 입력된 인증번호와 Session에 있는 인증번호 비교
	 * 
	 * @param map : 전달받은 JSON -> MAP 변경하여 저장
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, Object> map) {

		// 입력 받은 이메일, 인증 번호가 DB에 있는지 조회
		// 이메일 있고 인증번호 일치 == 1
		// 아니면 0
		return service.checkAuthKey(map);
	}

	// 닉네임 확인
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("memberNickname") String memberNickname) {

		return service.checkNickname(memberNickname);
	}

}
