package edu.kh.gowith.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.service.BoardService;
import edu.kh.gowith.main.model.service.MainService;
import jakarta.mail.Service;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService service;
	
	@RequestMapping("") // "/" 요청 매핑, 모든 메서드 요청 받아내기(get post 구분 x)
	public String mainPage(
			Model model ) {
		
		String value = "popDefault";
		List<Board> popBoard = service.popDefault(value);
        model.addAttribute("popBoard", popBoard);
		
		return "common/index";
	}
	
	// 공지사항, 전체글
	
	
	
	// 메인페이지에서 인기글 조회
	@ResponseBody
	@GetMapping("popWriteInquiry")
	public List<Board> popWriteInquiry(
		@RequestParam("popWriteBtn") String value,
		Model model
		) {
		
		List<Board> popBoard = new ArrayList<>();
		
		// 인기글 top 10 조회
		if(value.equals("popDefault") ) {
			popBoard = service.popDefault(value);
		}
		
		// 좋아요 개수에 따라 조회
		if(value.equals("like") ) {
			popBoard = service.popLike(value);
		}
		
		// 댓글 갯수에 따라 조회
		if(value.equals("comment")) {
			popBoard = service.popComment(value);
		}
		
		
		System.out.println("value : " + value );
		
		
		return popBoard;
	}
	
	
	
	
}

