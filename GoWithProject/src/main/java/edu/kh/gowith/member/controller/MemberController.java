package edu.kh.gowith.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@SessionAttributes({ "loginMember" })
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

	private final MemberService service;

	@PostMapping("login")
	public String loginMember(@ModelAttribute Member member, RedirectAttributes ra, Model model) {

		Member loginMember = service.loginMember(member);

		if (loginMember == null)
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다...");

		if (loginMember != null)
			model.addAttribute("loginMember", loginMember);

		return "/";
	}

	// 빠른 로그인
	@GetMapping("quickLogin")
	public String quikLogin(@RequestParam("memberEmail") String memberEmail, Model model, RedirectAttributes ra) {

		Member loginMember = service.quickLogin(memberEmail);

		if (loginMember == null) {
			ra.addFlashAttribute("message", "해당 이메일 회원이 존재하지 않습니다");

		} else {
			model.addAttribute("loginMember", loginMember); // @SessionAttribute가 session으로 올려준다
		}

		return "redirect:/";
	}

}
