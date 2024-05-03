package edu.kh.gowith.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.Comment;
import edu.kh.gowith.board.model.service.BoardService;
import edu.kh.gowith.main.model.service.MainService;
import edu.kh.gowith.member.model.dto.Member;
import jakarta.mail.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

	private final MainService service;

	@RequestMapping("")
	public String mainPage(Model model) {
		
		// 공지사항 조회
		List<Board> notification = service.notification();
		model.addAttribute("notification", notification);
		
		// 최신글 조회
		List<Board> listWithThumbnail = service.listWithThumbnail();
		model.addAttribute("listWithThumbnail", listWithThumbnail);
		System.out.println("썸넬리스트 :" + listWithThumbnail);

		// 인기글 조회
		String value = "popDefault";
		List<Board> popBoard = service.popDefault(value);
		model.addAttribute("popBoard", popBoard);

		// 최신댓글조회
		List<Comment> commentList = service.commentList();
		model.addAttribute("commentList", commentList);
		
		
		// 활동 많이한 회원조회
		String memberRankValue ="memDefault";
		List<Member> memRank = service.memDefault(value);
		model.addAttribute("value", value);
		
		
		
		
		
		
		
			
		return "common/index";
	}

	// 공지사항, 전체글

	// 메인페이지에서 인기글 조회
	@ResponseBody
	@GetMapping("popWriteInquiry")
	public List<Board> popWriteInquiry(@RequestParam("popWriteBtn") String value, Model model) {

		List<Board> popBoard = new ArrayList<>();

		// 인기글 top 10 조회
		if (value.equals("popDefault")) {
			popBoard = service.popDefault(value);
		}

		// 좋아요 개수에 따라 조회
		if (value.equals("like")) {
			popBoard = service.popLike(value);
		}

		// 댓글 갯수에 따라 조회
		if (value.equals("comment")) {
			popBoard = service.popComment(value);
		}

		System.out.println("value : " + value);

		return popBoard;
	}

	// 메인페이지에서 멤버 랭킹 조회
	@ResponseBody
	@GetMapping("memberRank")
	public List<Member> memberRank(@RequestParam("rank") String memberRankValue, Model model){
	
		List<Member> memRank = new ArrayList<>();
		
		// 댓글 많이 단 사람
		if(memberRankValue.equals("memDefault")) {
			memRank = service.memDefault(memberRankValue);
		}
		
		// 게시글 많이 쓴 사람
		if(memberRankValue.equals("board")) {
			memRank = service.memBoard(memberRankValue);
		}
		
		if(memberRankValue.equals("visit")) {
			memRank = service.memVisit(memberRankValue);
		}
		
		return memRank;
		
	}
	
	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
		return "redirect:/";
	}
	
	

}
