package edu.kh.gowith.comment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.gowith.board.model.dto.Comment;
import edu.kh.gowith.comment.model.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping("comment")
@Slf4j
public class CommentController {

	private CommentService service;
	
	@GetMapping("selectComment")
	@ResponseBody
	private List<Comment> selectComment(
			@RequestParam("boardNo") int boardNo,
			@RequestParam("checkComment") int checkComment
			){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("boardNo", boardNo);
		paramMap.put("checkComment", checkComment);
		
		List<Comment> commentList = service.selectComment(paramMap);
		
		
		
		return commentList;
	}
	
	@PostMapping("enroll")
	@ResponseBody
	private int enrollComment(
			@RequestBody Comment comment
			) {
				
		return service.enrollComment(comment);
	}
	
	@PostMapping("relpyComment")
	@ResponseBody
	private int replyComment(
			@RequestBody Comment comment
			) {

		return service.replyComment(comment);
	}
	
	@DeleteMapping("delete")
	@ResponseBody
	private int deleteComment(
			@RequestBody int commentNo
			) {
		
		return service.deleteComment(commentNo);
	}
	
	@PutMapping("update")
	@ResponseBody
	private int updateComment(
			@RequestBody Comment comment
			) {
		
		return service.updateComment(comment);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
