package edu.kh.gowith.main.model.service;

import java.util.List;

import edu.kh.gowith.board.model.dto.Board;

public interface MainService {

	// 탑텐(기본)
	List<Board> popDefault(String value);
	
	// 좋아요 개수에 따라
	List<Board> popLike(String value);

	// 댓글 개수에 따라
	List<Board> popComment(String value);

	

	

}
