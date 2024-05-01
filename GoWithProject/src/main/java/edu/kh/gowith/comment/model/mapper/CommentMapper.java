package edu.kh.gowith.comment.model.mapper;


import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

	int enrollComment(Comment comment);

	int replyComment(Comment comment);

	int deleteComment(int commentNo);

	int checkTag(String tagWord);

	void insertTag(String tagWord);

	void insertCommentTag(int tagNo);

	int selectTagNo(String tagWord);

	int updateComment(Comment comment);


}
