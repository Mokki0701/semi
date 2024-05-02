package edu.kh.gowith.board.model.service;

import java.util.List;
import java.util.Map;


import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BoardImg;
import edu.kh.gowith.board.model.dto.BottomMenu;


public interface BoardService {

	int boardFavorite(Map<String, String> paramMap);

	Map<String, Object> boardList(int bottomMenuCode, int cp, int limit, Map<String,Integer> inputMap);

	Map<String, Object> searchBoardList(Map<String, Object> paramMap, int cp, int limit,
			Map<String, Integer> inputMap);

	int boardInsertFavorite(Map<String, String> paramMap);

	List<BottomMenu> selectBottmList(int topMenuCode);
	
	Board boardDetail(Map<String, Object> paramMap);

	int updateReadCount(int boardNo);

	String bottomMenuName(int bottomMenuCode);

	int boardLike(Map<String, Integer> paramMap);


}
