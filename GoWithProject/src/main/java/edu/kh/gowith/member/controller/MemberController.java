package edu.kh.gowith.member.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.member.model.dto.MemberMenu;
import edu.kh.gowith.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@SessionAttributes({ "loginMember", "postCounter", "favorBoard" })
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

	private final MemberService service;

	// 빠른 로그인
	@GetMapping("quickLogin")
	public String quikLogin(@RequestParam("memberEmail") String memberEmail, Model model, RedirectAttributes ra) {

		Member loginMember = service.quickLogin(memberEmail);

		if (loginMember == null) {
			ra.addFlashAttribute("message", "해당 이메일 회원이 존재하지 않습니다");

			// 빠른 로그인 성공 시
		} else {
			int postCounter = service.postCounter(loginMember.getMemberNo());
			
			List<MemberMenu> favorBoard = service.favorBoard(loginMember.getMemberNo());

			// 콘솔로 확인
			MemberMenu[] favorBoardArray = new MemberMenu[favorBoard.size()];
			favorBoard.toArray(favorBoardArray);
			System.out.println("favorBoardArray" + favorBoardArray);

			model.addAttribute("loginMember", loginMember);
			model.addAttribute("postCounter", postCounter);
			model.addAttribute("favorBoard", favorBoard);

		} 

		return "redirect:/";
	}

	@PostMapping("login")
	public String loginMember(@ModelAttribute Member member, RedirectAttributes ra, Model model) {

		Member loginMember = service.loginMember(member);

		if (loginMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		if (loginMember != null) {
			int postCounter = service.postCounter(loginMember.getMemberNo());
			model.addAttribute("loginMember", loginMember);
			model.addAttribute("postCounter", postCounter);
		}

		return "redirect:/";
	}

	// 로그아웃
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}

}
