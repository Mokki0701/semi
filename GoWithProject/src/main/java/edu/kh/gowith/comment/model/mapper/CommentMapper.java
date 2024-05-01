package edu.kh.gowith.comment.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

	int enrollComment(Comment comment);

}
