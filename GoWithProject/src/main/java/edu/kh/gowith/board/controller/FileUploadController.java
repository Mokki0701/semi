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
			@RequestParam(value="managerCheck",required= false) String agree		
			) throws IllegalStateException, IOException {
		
		inputBoard.setTopMenuCode(topMenuCode);
		inputBoard.setBottomBoardCode(bottomMenuCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		String path = null;
		String message = null;
		
		int boardNo = 0;
		
		//managerCheck 파라미터 있음 == 관리자 회원
		if(agree != null) {
			inputBoard.setBoardNotification("Y");
			//성공 시 상세조회 요청할 수 있도록 삽입된 게시글 번호 반환 
			boardNo = service.notiInsert(inputBoard,images);
		} else {
		//managerCheck 파라미터 없음 == 일반회원
			//성공 시 상세조회 요청할 수 있도록 삽입된 게시글 번호 반환 
			boardNo = service.boardInsert(inputBoard,images);
			
			
			if(boardNo>0) {
				//성공시 상세페이지 보내기
				//path = ""
				//message = "게시글이 작성 되었습니다";
			}else {
				//실패시 글쓰기 페이지로 redirect
				//path= String.format("redirect:/board/%d/%d")
				//message = "게시글 작성 실패";
			}
		}
		//ra.addFlashAttribute("message",message);
		
		
		
		//관리자 공지글 등록할 경우
//		if("yes".equals(agree)) {
//			inputBoard.setBoardNotification("Y");
//			boardNo = service.notiInsert(inputBoard,images);
//			
//		}else {
//			// 일반 회원 게시글 등록
//			//성공 시 상세조회 요청할 수 있도록 삽입된 게시글 번호 반환 
//			boardNo = service.boardInsert(inputBoard,images);
//			
//			
//			if(boardNo>0) {
//				//성공시 상세페이지 보내기
//				//path = ""
//				//message = "게시글이 작성 되었습니다";
//			}else {
//				//실패시 글쓰기 페이지로 redirect
//				//path= String.format("redirect:/board/%d/%d")
//				//message = "게시글 작성 실패";
//			}
//			
//			
//		}
		
		//ra.addFlashAttribute("message",message);
		
		return "/boardWrite/test";
	}
	
	@ResponseBody
	@GetMapping("bottomCode")
	public List<BottomMenu> getBottomCode(
			@RequestParam("topMenuCode") int topMenuCode
			) {
		
		return service.selectBottomCodes(topMenuCode);
	}
	
	
	
	
}
