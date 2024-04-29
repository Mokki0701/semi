package edu.kh.gowith.comment.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.gowith.board.model.dto.Comment;
import edu.kh.gowith.board.model.mapper.BoardMapper;
import edu.kh.gowith.comment.model.mapper.CommentMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	private CommentMapper mapper;
	private BoardMapper mapper2;
	
	@Override
	public List<Comment> selectComment(Map<String, Object> paramMap) {

		return mapper2.commentList(paramMap);
	}
	
}
