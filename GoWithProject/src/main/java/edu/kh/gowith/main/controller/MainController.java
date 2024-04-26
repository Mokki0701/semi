package edu.kh.gowith.main.controller;

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
	public String mainPage() {
		
		return "common/index";
	}
	
	
	// 메인페이지에서 인기글 조회
	@ResponseBody
	@GetMapping("popWriteInquiry")
	public List<Board> popWriteInquiry(
		@RequestParam("popWriteBtn") String value
		) {
		
		List<Board> popBoardInquiry = service.popBoardInquiry(value);
		
		return popBoardInquiry;
	}
	
	
	
	
}

