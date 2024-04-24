package edu.kh.gowith.board.model.service;

import java.util.Map;

public interface BoardService {

	int boardFavorite(Map<String, String> paramMap);

	Map<String, Object> boardList(int bottomMenuCode, int cp, int limit, int memberNo);

}
