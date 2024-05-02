package edu.kh.gowith.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.Comment;
import edu.kh.gowith.board.model.dto.Pagination;
import edu.kh.gowith.board.model.dto.TopMenu;
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
	public int boardInsertFavorite(Map<String, String> paramMap) {
		
		return mapper.boardInsertFavorite(paramMap);
	}
	
	
	@Override
	public Map<String, Object> boardList(int bottomMenuCode, int cp, int limit, Map<String,Integer> inputMap) {
		
		int listCount = mapper.getListCount(bottomMenuCode);
		
		Pagination pagination = new Pagination(cp, listCount, limit);
		
		int offset = (cp - 1) * limit;
		
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.getBoardList(bottomMenuCode,rowBounds);
		
		Map<String, Object> mapList = new HashMap<>();
		
		if(inputMap.get("loginMemberNo") != null) {
			
			int loginMemberNo = inputMap.get("loginMemberNo");
			
			Map<String, Integer> checkFavorite = new HashMap<>();
			checkFavorite.put("bottomMenuCode", bottomMenuCode);
			checkFavorite.put("loginMemberNo", loginMemberNo);
			
			int favoriteCheck = mapper.getFavorite(checkFavorite);
			
			mapList.put("favoriteCheck", favoriteCheck);
		}
		
		
		
		List<TopMenu> topMenuList = mapper.bottomTopMenu();
		
		String topMenuName = null;
		
		for(int i = 0; i < topMenuList.size(); i++) {
			if(bottomMenuCode == topMenuList.get(i).getTopMenuCode()) {
				topMenuName = topMenuList.get(i).getTopMenuName();
				break;
			}
		}
		
		mapList.put("topMenuName", topMenuName);
		mapList.put("pagination", pagination);
		mapList.put("boardList", boardList);
		mapList.put("topMenuList", topMenuList);
		
		return mapList;
	}
	
	
	@Override
	public Map<String, Object> searchBoardList(Map<String, Object> paramMap, int cp, int limit,
			Map<String, Integer> inputMap) {
		
		int listCount = mapper.getSearchCount(paramMap);
		
		Pagination pagination = new Pagination(cp, listCount, limit);
		
		int offset = (cp - 1) * limit;
		
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.getSearchList(paramMap,rowBounds);
		
		Map<String, Object> mapList = new HashMap<>();
		
		Map<String, Integer> checkFavorite = new HashMap<>();
	
//		try {
//			
//		}catch (Exception e) {
//			checkFavorite.put("bottomMenuCode", Integer.parseInt(String.valueOf(paramMap.get("bottomMenuKey"))));
//		}
		
		if(inputMap.get("loginMemberNo") != null) {
			
			int loginMemberNo = inputMap.get("loginMemberNo");
			
			checkFavorite.put("bottomMenuCode", Integer.parseInt(String.valueOf(paramMap.get("bottomMenuKey"))));
			checkFavorite.put("loginMemberNo", loginMemberNo);
			
			int favoriteCheck = mapper.getFavorite(checkFavorite);
			
			mapList.put("favoriteCheck", favoriteCheck);
		}
		
		List<TopMenu> topMenuList = mapper.bottomTopMenu();
		
		String topMenuName = null;
		
		if(paramMap.get("bottomMenuKey") != null) {
			
			for(int i = 0; i < topMenuList.size(); i++) {
				
				
				if(mapper.getBottomName(Integer.parseInt(String.valueOf(paramMap.get("bottomMenuKey")))) == topMenuList.get(i).getTopMenuCode()) {
					topMenuName = topMenuList.get(i).getTopMenuName();
					break;
					
				}
			}
			
		}
		
		
		mapList.put("topMenuName", topMenuName);
		mapList.put("pagination", pagination);
		mapList.put("boardList", boardList);
		mapList.put("topMenuList", topMenuList);
		
		return mapList;
	}
	
	
	@Override
	public List<BottomMenu> selectBottmList(int topMenuCode) {

		return mapper.selectBottomList(topMenuCode);
	}
	
	
	@Override
	public Board boardDetail(Map<String, Object> paramMap) {
		
		Board board = mapper.boardDetail(paramMap);
		
		List<Comment> comment = mapper.commentList(paramMap);
		board.setCommentList(comment);
		
		return board;
	}
	
	
	@Override
	public int updateReadCount(int boardNo) {

		int result = mapper.increaseReadCount(boardNo);
		
		if(result > 0) return mapper.resultReadCount(boardNo);
		
		return -1;
	}
	
	@Override
	public String bottomMenuName(int bottomMenuCode) {
		return mapper.bottomMenuName(bottomMenuCode);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
