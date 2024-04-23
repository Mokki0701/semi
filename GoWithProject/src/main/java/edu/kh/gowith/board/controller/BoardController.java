package edu.kh.gowith.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.gowith.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService service;
	
	@PutMapping("favorite")
	@ResponseBody
	public String boardFavorite(
			@RequestBody Map<String, String> paramMap
			) {
		
		return service.boardFavorite(paramMap);
	}
	
}
