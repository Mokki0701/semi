package edu.kh.gowith.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.gowith.board.model.dto.Board;

public interface BoardService {

	int boardFavorite(Map<String, String> paramMap);

	Map<String, Object> boardList(int bottomMenuCode, int cp, int limit, int memberNo);

	Map<String, Object> searchBoardList(Map<String, Object> paramMap, int cp, int limit,
			int memberNo);

	int boardInsertFavorite(Map<String, String> paramMap);

	

}
