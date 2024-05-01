package edu.kh.gowith.comment.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.gowith.board.model.dto.Comment;

public interface CommentService {

	List<Comment> selectComment(Map<String, Object> paramMap);

	int enrollComment(Comment comment);

	int replyComment(Comment comment);

	int deleteComment(int commentNo);

	int updateComment(Comment comment);

}
