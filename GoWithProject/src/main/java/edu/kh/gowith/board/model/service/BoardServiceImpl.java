package edu.kh.gowith.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.MemberMenu;
import edu.kh.gowith.board.model.dto.Pagination;
import edu.kh.gowith.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper;
	
	@Override
	public int boardFavorite(Map<String, String> paramMap) {
		
		return mapper.boardFavortie(paramMap);
	}
	
	@Override
	public Map<String, Object> boardList(int bottomMenuCode, int cp, int limit, int loginMemberNo) {
		
		int listCount = mapper.getListCount(bottomMenuCode);
		
		Pagination pagination = new Pagination(cp, listCount, limit);
		
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.getBoardList(bottomMenuCode,rowBounds);
		
		Map<String, Object> mapList = new HashMap<>();
		
		Map<String, Integer> checkFavorite = new HashMap<>();
		checkFavorite.put("bottomMenuCode", bottomMenuCode);
		checkFavorite.put("loginMemberNo", loginMemberNo);
		
		MemberMenu memberMenu = mapper.getFavorite(checkFavorite);
		
		mapList.put("pagination", pagination);
		mapList.put("boardList", boardList);
		mapList.put("memberMenu", memberMenu);
		
		return mapList;
	}
	
	
	
	
	
	
	
	
}