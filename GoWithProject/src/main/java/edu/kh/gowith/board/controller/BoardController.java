package edu.kh.gowith.board.controller;

import java.beans.JavaBean;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.gowith.board.model.service.BoardService;
import edu.kh.gowith.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
@JavaBean
public class BoardController {

	private final BoardService service;
	
	@GetMapping("{topMenuCode:[0-9]+}/{bottomMenuCode:[0-9]+}")
	public String boardList(
			@PathVariable("topMenuCode") int topMenuCode,
			@PathVariable("bottomMenuCode") int bottomMenuCode,
			@RequestParam(value="cp", required = false, defaultValue= "1") int cp,
			@RequestParam(value="limit", required = false, defaultValue="10") int limit,
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			Model model
			) {
		
		Map<String, Object> boardMap = service.boardList(bottomMenuCode, cp, limit, loginMember.getMemberNo());
		
		model.addAttribute("pagination", boardMap.get("pagination"));
		model.addAttribute("boardList", boardMap.get("boardList"));
		model.addAttribute("memberMenu", boardMap.get("memberMenu"));
		
		return "board/boardList";
	}
	
	@PutMapping("favorite")
	@ResponseBody
	public int boardFavorite(
			@RequestBody Map<String, String> paramMap
			) {
		
		return service.boardFavorite(paramMap);
	}
	
}
