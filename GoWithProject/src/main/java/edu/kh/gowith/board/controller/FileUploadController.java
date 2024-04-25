package edu.kh.gowith.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.gowith.board.model.dto.Board;
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
	public String boardInsert() {
		
		return "boardWrite/boardWrite";
	}
	
	@PostMapping("{topMenuCode:[0-9]+}/{bottomMenuCode:[0-9]+}/insert")
	public String boardInsert(
			@PathVariable("topMenuCode") int topMenuCode,
			@PathVariable("bottomMenuCode") int bottomMenuCode,
			@ModelAttribute Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra
			) throws IllegalStateException, IOException {
		
		inputBoard.setTopMenuCode(topMenuCode);
		inputBoard.setBottomBoardCode(bottomMenuCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		//성공 시 상세조회 요청할 수 있도록 삽입된 게시글 번호 반환 
		int boardNo = service.boardInsert(inputBoard,images);
	
		String path = null;
		String message = null;
		
		if(boardNo>0) {
			//path = ""
			//message = "게시글이 작성 되었습니다";
		}else {
			//path= String.format("redirect:/board/%d/%d")
			//message = "게시글 작성 실패";
		}
		
		//ra.addFlashAttribute("message",message);
		
		return path;
	}
	
	
	
	
	
	
	
}
