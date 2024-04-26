package edu.kh.gowith.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.service.BoardService;
import edu.kh.gowith.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService service;
	
	@GetMapping("{topMenuCode:[0-9]+}/{bottomMenuCode:[0-9]+}")
	public String boardList(
			@PathVariable("topMenuCode") int topMenuCode,
			@PathVariable("bottomMenuCode") int bottomMenuCode,
			@RequestParam(value="cp", required = false, defaultValue= "1") int cp,
			@RequestParam(value="limit", required = false, defaultValue="10") int limit,
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			@RequestParam(value="topMenuKey", required = false) String searchTopMenu,
			@RequestParam(value="bottomMenuKey", required = false) String searchBottomMenu,
			@RequestParam(value="periodKey", required = false) String searchDate,
			@RequestParam(value="searchKey", required = false) String searchformKey,
			@RequestParam(value="query", required = false) String searchQuery,
			@RequestParam Map<String, Object> paramMap,
			Model model	
			) {
		
		Map<String, Object> boardMap = null;
		
		if(paramMap.get("periodKey") == null) {
			Map<String, Integer> inputMap = new HashMap<>();
			
			if(loginMember != null) {
				inputMap.put("loginMemberNo", loginMember.getMemberNo());
				
			}
						
			boardMap = service.boardList(bottomMenuCode, cp, limit, inputMap);
			
			model.addAttribute("queryStringLimit", "?cp="+cp);
			 
			model.addAttribute("queryStringCp", "limit=" + limit);
			
			model.addAttribute("queryStringDetail", "limit=" + limit + "&cp=" + cp);

		}else {
			
			Map<String, Integer> inputMap = new HashMap<>();
			
			if(loginMember != null) {
				inputMap.put("loginMemberNo", loginMember.getMemberNo());
			}
			
			boardMap = service.searchBoardList(paramMap, cp, limit, inputMap);
			
			model.addAttribute("queryStringLimit", "?cp="+cp + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="
					+searchBottomMenu+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery);
			
			model.addAttribute("queryStringCp", "limit=" + limit + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="
					+searchBottomMenu+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery);
			
			model.addAttribute("queryStringDetail", "limit=" + limit + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="
					+searchBottomMenu+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery + "&cp=" + cp);
			
		}
		
		
		model.addAttribute("pagination", boardMap.get("pagination"));
		model.addAttribute("boardList", boardMap.get("boardList"));
		model.addAttribute("favoriteCheck", boardMap.get("favoriteCheck"));
		model.addAttribute("topMenuList", boardMap.get("topMenuList"));
		model.addAttribute("topMenuName", boardMap.get("topMenuName"));
		
		return "board/boardList";
	}
	
	@DeleteMapping("favorite")
	@ResponseBody
	public int boardFavorite(
			@RequestBody Map<String, String> paramMap
			) {
		
		return service.boardFavorite(paramMap);
	}
	
	
	@PostMapping("favorite")
	@ResponseBody
	public int boardInsertFavorite(
			@RequestBody Map<String, String> paramMap
			) {
		
		return service.boardInsertFavorite(paramMap);
	}	
	
	@GetMapping("selectBottom")
	@ResponseBody
	public List<BottomMenu> selectBottomList(
			@RequestParam("topMenuCode") int topMenuCode
			){
		
		return service.selectBottmList(topMenuCode);
	}
	
	
	
	@GetMapping("{topMenuCode:[0-9]+}/{bottomMenuCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("boardNo") int boardNo,
			Model model	
			) {
		Map<String, Object> paramMap = service.boardDetail(boardNo);
		
		
		return "board/boardDetail";
	}
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
}
