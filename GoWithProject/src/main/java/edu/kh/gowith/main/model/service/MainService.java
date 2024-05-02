package edu.kh.gowith.main.model.service;

import java.util.List;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.Comment;
import edu.kh.gowith.member.model.dto.Member;

public interface MainService {

	// 탑텐(기본)
	List<Board> popDefault(String value);
	
	// 좋아요 개수에 따라
	List<Board> popLike(String value);

	// 댓글 개수에 따라
	List<Board> popComment(String value);

	// 최근댓글 동기식 조회
	List<Comment> commentList();


	// 멤버 랭킹 댓글 (기본)
	List<Member> memDefault(String memberRank);

	// 멤버 랭킹 게시글 
	List<Member> memBoard(String memberRankValue);

	// 멤버 랭킹 방문
	List<Member> memVisit(String memberRankValue);

	// 썸네일 리스트 6개 조회
	List<Board> listWithThumbnail();

	// 
	List<Board> notification();


	

	

}
