package edu.kh.gowith.comment.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import edu.kh.gowith.board.model.dto.Comment;
import edu.kh.gowith.board.model.mapper.BoardMapper;
import edu.kh.gowith.comment.model.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
	
	private CommentMapper mapper;
	private BoardMapper mapper2;
	
	@Override
	public List<Comment> selectComment(Map<String, Object> paramMap) {

		return mapper2.commentList(paramMap);
	}
	
	@Override
	public int enrollComment(Comment comment) {
		
		// 태그 등록 기능
		String commentContent = comment.getCommentContent();
		Pattern pattern = Pattern.compile("#[\\w\\dㄱ-힣]+");
		Matcher matcher = pattern.matcher(commentContent);

		List<String> tagWords = new ArrayList<>();
		while (matcher.find()) {
		    tagWords.add(matcher.group());
		}
		
		int result = mapper.enrollComment(comment);
		
		// 태그가 있는지 조회
		for(String tagWord : tagWords) {
			int result2 = mapper.checkTag(tagWord);		
			
			if(result2 == 0) {
				mapper.insertTag(tagWord);
			}
			int tagNo = mapper.selectTagNo(tagWord);
			
			mapper.insertCommentTag(tagNo);
			
		}
		
		
		return result;
	}
	
	@Override
	public int replyComment(Comment comment) {
		
		return mapper.replyComment(comment);
	}
	
	@Override
	public int deleteComment(int commentNo) {
	
		return mapper.deleteComment(commentNo);
	}

	
	@Override
	public int updateComment(Comment comment) {
		
		// 태그 수정 기능
		mapper.deleteCommentTag(comment.getCommentNo());
		
		String commentContent = comment.getCommentContent();
		Pattern pattern = Pattern.compile("#[\\w\\dㄱ-힣]+");
		Matcher matcher = pattern.matcher(commentContent);

		List<String> tagWords = new ArrayList<>();
		while (matcher.find()) {
		    tagWords.add(matcher.group());
		}
		
		// 태그가 있는지 조회
		for(String tagWord : tagWords) {
					
			int result2 = mapper.checkTag(tagWord);
			
			if(result2 == 0) mapper.insertTag(tagWord);
			
			int tagNo = mapper.selectTagNo(tagWord);
			
			Map<String, Object> paramMap = new HashMap<>();
			
			paramMap.put("commentNo", comment.getCommentNo());
			paramMap.put("tagNo", tagNo);
			
			mapper.insertCommentTaggg(paramMap);
			}
		
		
		return mapper.updateComment(comment);
	}
	
	
	
}
