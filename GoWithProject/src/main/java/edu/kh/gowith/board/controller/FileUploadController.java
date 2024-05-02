package edu.kh.gowith.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BoardImg;
import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.TopMenu;
import edu.kh.gowith.board.model.service.BoardWriteService;
import edu.kh.gowith.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("editBoard")
@Slf4j
public class FileUploadController {
	
	private final BoardWriteService service;
	
	//글쓰기 화면 보이게
	@GetMapping("{topMenuCode:[0-9]+}/{bottomMenuCode:[0-9]+}/insert")
	public String boardInsert(
			@PathVariable("topMenuCode") int topMenuCode,
			@PathVariable("bottomMenuCode") int bottomMenuCode,
			@SessionAttribute("loginMember") Member loginMember,
			Model model
			) {

		// 게시글 목록 조회 -> 상위 메뉴 이름만 전달하기
		String boardTitle = service.selectTitle(topMenuCode);
		
		model.addAttribute("boardTitle",boardTitle);
		model.addAttribute(loginMember);
		model.addAttribute("bottomMenuCode",bottomMenuCode);
		
		// Top메뉴 코드 리스트 반환
		List<TopMenu> topMenuList = service.topMenuCodeList();
		model.addAttribute("topMenuList",topMenuList);
		
		// Bottom 메뉴 코드 리스트 반환
		List<BottomMenu> bottomMenuList = getBottomCode(topMenuCode);
		model.addAttribute("bottomMenuList",bottomMenuList);
		
		return "boardWrite/boardWrite";
	}
	
	
	
	@PostMapping("{topMenuCode:[0-9]+}/{bottomMenuCode:[0-9]+}/insert")
	public String boardInsert(
			@PathVariable("topMenuCode") int topMenuCode,
			@PathVariable("bottomMenuCode") int bottomMenuCode,
			@ModelAttribute Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra,
			@RequestParam(value="managerCheck",required= false) String agree,
			@RequestParam(value="topMenu", required = false) Integer selectTopMenu,
			@RequestParam(value="selectMenu", required = false) Integer selectMenu,
			@RequestParam(value="bottomMenu", required = false) Integer selectBottomMenu
			) throws IllegalStateException, IOException {
		
		inputBoard.setTopMenuCode(topMenuCode);
		inputBoard.setBottomBoardCode(bottomMenuCode);
		
		//전체 글쓰기 말머리 선택시
		if(selectTopMenu != null) {
			inputBoard.setTopMenuCode(selectTopMenu);
		}
		
		if(selectMenu != null) {
			inputBoard.setBottomBoardCode(selectMenu);
		}
		
		// 하위 메뉴 글쓰기 말머리 클릭시
		if(selectBottomMenu != null) {
			inputBoard.setBottomBoardCode(selectBottomMenu);
		}
		
		inputBoard.setMemberNo(loginMember.getMemberNo());
		String path = null;
		String message = null;
		
		int boardNo = 0;
		
		//managerCheck 파라미터 있음 == 관리자 회원
		if(agree != null) {
			inputBoard.setBoardNotification("Y");
			//성공 시 상세조회 요청할 수 있도록 삽입된 게시글 번호 반환 
			boardNo = service.notiInsert(inputBoard,images);
		} else {//managerCheck 파라미터 없음 == 일반회원
		
			//성공 시 상세조회 요청할 수 있도록 삽입된 게시글 번호 반환 
			boardNo = service.boardInsert(inputBoard,images);
			
			
			if(boardNo>0) {
				//성공시 상세페이지 보내기
				path = String.format("/board/%d/%d/%d", inputBoard.getTopMenuCode(),inputBoard.getBottomBoardCode(),boardNo);
				message = "게시글이 작성 되었습니다";
			}else {
				//실패시 글쓰기 페이지로 redirect
				path= "insert";
				message = "게시글 작성 실패";
			}
		}
		//ra.addFlashAttribute("message",message);
		
		
		
		
		return "redirect:"+path;
	}
	
	@ResponseBody
	@GetMapping("bottomCode")
	public List<BottomMenu> getBottomCode(
			@RequestParam("topMenuCode") int topMenuCode
			) {
		
		return service.selectBottomCodes(topMenuCode);
	}
	
	
	//게시글 수정 화면 전환
	@GetMapping("{topMenuCode:[0-9]+}/{bottomMenuCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(
			@PathVariable("topMenuCode") int topMenuCode,
			@PathVariable("bottomMenuCode") int bottomMenuCode,
			@PathVariable("boardNo") int boardNo,
			@SessionAttribute("loginMember") Member loginMember,
			Model model, RedirectAttributes ra
			) {

		// 게시글 제목, 내용 조회하기
		Board updateBoard = service.searchBoard(boardNo);
		
		// 게시판 이름
		String boardTitle = service.selectTitle(topMenuCode);
		
		//이미지 조회해 오기
		
		List<BoardImg> imgList = service.imgList(boardNo);
		
		
		String message = null;
		String path = null;
		
		if(updateBoard == null) {
			message = "해당 게시글이 존재하지 않습니다";
			path = "redirect:/";
			ra.addFlashAttribute("message",message);
		}else if(updateBoard.getMemberNo() != loginMember.getMemberNo()) {
			message="자신이 작성한 글만 수정 가능합니다";
			path = String.format("redirect:/board/%d/%d/%d", topMenuCode, bottomMenuCode, boardNo );
			ra.addFlashAttribute("message",message);
		}else {
			path = "boardWrite/boardUpdate";
			model.addAttribute("updateBoard",updateBoard);
			model.addAttribute("boardTitle",boardTitle);
			model.addAttribute("imgList",imgList);
		}
		
		return path;
	}
	
	
	@PostMapping("{topMenuCode:[0-9]+}/{bottomMenuCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(
			@PathVariable("topMenuCode") int topMenuCode,
			@PathVariable("bottomMenuCode") int bottomMenuCode,
			@PathVariable("boardNo") int boardNo,
			@ModelAttribute Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra,
			@RequestParam(value="managerCheck",required= false) String agree,
			@RequestParam(value="deleteOrder",required=false, defaultValue="") String deleteOrder ,
			@RequestParam(value="querystring",required=false, defaultValue="") String querystring
			
			) throws IllegalStateException, IOException {
		
		inputBoard.setTopMenuCode(topMenuCode);
		inputBoard.setBottomBoardCode(bottomMenuCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		int result = service.notiUpdate(inputBoard,images,deleteOrder);
		
		String message = null;
		String path = null;
		
		if(result >0) {
			message = "게시글이 수정 되었습니다";
			path = String.format("/board/%d/%d/%d%s", topMenuCode,bottomMenuCode,boardNo,querystring);
		} else {
			message = "수정 실패";
			path = "update"; //수정 화면 전환 상대 경로
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:"+path;
		
	}
	
	
	
	
	
}
