package edu.kh.gowith.board.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BoardImg;
import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.service.BoardService;
import edu.kh.gowith.member.model.dto.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
			
			if(searchBottomMenu == null) {
				
				model.addAttribute("queryStringLimit", "?cp="+cp + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery);
				
				model.addAttribute("queryStringCp", "limit=" + limit + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery);
				
				model.addAttribute("queryStringDetail", "limit=" + limit + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery + "&cp=" + cp);
				
			}
			
			else {
				
				model.addAttribute("queryStringLimit", "?cp="+cp + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="
						+searchBottomMenu+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery);
				
				model.addAttribute("queryStringCp", "limit=" + limit + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="
						+searchBottomMenu+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery);
				
				model.addAttribute("queryStringDetail", "limit=" + limit + "&topMenuKey="+searchTopMenu+"&bottomMenuKey="
						+searchBottomMenu+"&periodKey="+searchDate+"&searchKey="+searchformKey +"&query="+searchQuery + "&cp=" + cp);
				
			}
			
			
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
			@PathVariable("topMenuCode") int topMenuCode,
			@PathVariable("bottomMenuCode") int bottomMenuCode,
			@RequestParam(value="limit", required = false, defaultValue="10") int limit,
			@RequestParam(value="cp", required = false, defaultValue= "1") int cp,
			
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			// @RequestParam("queryStringDetail") String queryStringDetail,
			HttpServletResponse resp,
			HttpServletRequest req,
			Model model	
			) throws ParseException {
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("boardNo", boardNo);
		paramMap.put("bottomMenuCode", bottomMenuCode);
		paramMap.put("checkComment", 1);
		
		if(loginMember != null) {
			paramMap.put("loginMember", loginMember);
		}
		
		Board board = service.boardDetail(paramMap);
		
		String path = null;
		
		if(board == null) {
			path = "redirect:/board/"+ topMenuCode + "/" + bottomMenuCode;
		}
		
		else {
			
			if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
				Cookie[] cookies = req.getCookies();
				
				Cookie c = null;
				for(Cookie temp : cookies) {
					if(temp.getName().equals("readBoardNo")) {
						c = temp;
						break;
					}
				}
				
				int result = 0;
				
				if(c == null) {
					
					c = new Cookie("readBoardNo", "[" + boardNo + "]");
					result = service.updateReadCount(boardNo);
					
				}
				
				else {
					
					if(c.getValue().indexOf("[" + boardNo + "]") == -1) {
						
						c.setValue(c.getValue() + "[" + boardNo + "]");
						result = service.updateReadCount(boardNo);
					}
					
				}
				
				if(result > 0) {
					
					board.setReadCount(result);
					
					c.setPath("/");
					
					Calendar cal = Calendar.getInstance();
					cal.add(cal.DATE, 1); 

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					Date a = new Date(); 

					Date temp = new Date(cal.getTimeInMillis()); 

					Date b = sdf.parse(sdf.format(temp)); 

					long diff = (b.getTime() - a.getTime()) / 1000;

					c.setMaxAge((int) diff); // 수명 설정

					resp.addCookie(c);
					
				}
			}
			
			path = "board/boardDetail";
			
			model.addAttribute("board", board);
			
			if(!board.getImgList().isEmpty()) {
				
				BoardImg thumbnail = null;
				
				if(board.getImgList().get(0).getImgOrder()==0) thumbnail = board.getImgList().get(0);
				
				
				model.addAttribute("thumbnail", thumbnail);
				model.addAttribute("start", thumbnail != null ? 1 : 0);
				
			}
			
		}
		String bottomMenuName = service.bottomMenuName(bottomMenuCode);
		
		model.addAttribute("bottomMenuName", bottomMenuName);
		model.addAttribute("limit", limit);
		model.addAttribute("cp", cp);
		
		return path;
	}
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
}
