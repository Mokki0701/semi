package edu.kh.gowith.myPage.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.myPage.model.dto.MyPage;
import edu.kh.gowith.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {
	
	private final MyPageService service;
	
	//내정보 조회 /수정 화면으로 전환
	
	@GetMapping("update")
	public String info(@SessionAttribute("loginMember") Member loginMember, Model model) {
		
		 String memberAddress = loginMember.getMemberAddress();
		 
		 if(memberAddress != null) {
			 String[] arr = memberAddress.split("\\^\\^\\^");
			 
			 model.addAttribute("postcode",arr[0]);
			 model.addAttribute("address", arr[1]);
			 model.addAttribute("detailAddress",arr[2]);
		 }
		 return "myPage/update";
	}
	
	
	
	//회원탈퇴화면 이동
	@GetMapping("delete")
	public String delete() {
		return "myPage/delete";
	}
	
	
	
	
	
	/** 회원 정보 수정
	 * @param inputMember : 제출된 회원 닉네임, 전화번호, 주소, 현재비번,새비번,새비번확인
	 * 
	 * @param loginMember : 로그인한 회원 정보 (회원 번호 사용할 예정)
	 * 
	 * @param memberAddress : 주소만 따로 받은 String[]
	 * 
	 * @param ra : 리다이렉트 시 request scope로 데이터 전달
	 * 
	 * @return
	 */
	@PostMapping("update")
	public String updateInfo(
			@ModelAttribute MyPage inputMember,
			@SessionAttribute("loginMember") MyPage loginMember,
			Model model,
			RedirectAttributes ra
			) {
		
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
	
		int result = service.updateInfo(inputMember);
		
		String message = null;
		
		if(result>0) {
			message = "회원 정보 수정 성공";
			
			
			
			loginMember.setMemberNickname(inputMember.getMemberNickname());
			loginMember.setMemberTel(inputMember.getMemberTel());
			loginMember.setMemberAddress(inputMember.getMemberAddress());
		}else {
			message = "회원정보 수정 실패...";
		}
		ra.addFlashAttribute("message", message);
		
		
		return "redirect:profile";
	}
	

		

	
	
	//회원탈퇴
	@PostMapping("delete")
	public String deleteMember(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("memberPw") String currentPw,
			RedirectAttributes ra,
			SessionStatus status) {
		
		int memberNo = loginMember.getMemberNo();
		int result = service.deleteMember(currentPw, memberNo);
		
		if(result>0) {
			ra.addFlashAttribute("message", "탈퇴 성공");
			status.setComplete();
			return "redirect:/";
		}else {
			ra.addAttribute("message", "비밀번호불일치-탈퇴실패");
		}
		return "redirect:delete";
	} 
	
	
	
	
	


}
