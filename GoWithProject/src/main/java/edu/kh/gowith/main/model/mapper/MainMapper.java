package edu.kh.gowith.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.Comment;
import edu.kh.gowith.member.model.dto.Member;

@Mapper
public interface MainMapper {

	
	// 탑텐조회(기본)
	List<Board> popDefault();

	// 좋아요 갯수 순으로 조회
	List<Board> popLike();

	// comment 갯수 순서로 조회
	List<Board> popComment();

	// 최근 댓글 동기식 조회
	List<Comment> commentList();

	// 멤버랭킹 댓글(기본)
	List<Member> memDefault();

	// 멤버랭킹 게시글
	List<Member> memBoard();

	// 멤버랭킹 방문
	List<Member> memVisit();

	// 썸네일 리스트 6개 조회
	List<Board> listWithThumbnail();

	// 공지 조회
	List<Board> notification();
	
	
	
	

	

	
}
