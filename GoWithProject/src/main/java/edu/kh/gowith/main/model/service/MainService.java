package edu.kh.gowith.main.model.service;

import java.util.List;

import edu.kh.gowith.board.model.dto.Board;

public interface MainService {

	
	// 인기글 비동기 조회
	List<Board> popBoardInquiry(String value);

}
