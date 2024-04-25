package edu.kh.gowith.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BottomMenu;
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
	public int boardInsertFavorite(Map<String, String> paramMap) {
		
		return mapper.boardInsertFavorite(paramMap);
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
		
		int favoriteCheck = mapper.getFavorite(checkFavorite);
		
		List<BottomMenu> bottomMeniList = mapper.bottomTopMenu();
		
		String bottomMenuName = null;
		
		for(int i = 0; i < bottomMeniList.size(); i++) {
			if(bottomMenuCode == bottomMeniList.get(i).getBottomMenuCode()) {
				bottomMenuName = bottomMeniList.get(i).getBottomMenuName();
				break;
			}
		}
		
		mapList.put("bottomMenuName", bottomMenuName);
		mapList.put("pagination", pagination);
		mapList.put("boardList", boardList);
		mapList.put("favoriteCheck", favoriteCheck);
		mapList.put("bottomMenuList", bottomMeniList);
		
		return mapList;
	}
	
	
	@Override
	public Map<String, Object> searchBoardList(Map<String, Object> paramMap, int cp, int limit,
			int loginMemberNo) {
		
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
		
		checkFavorite.put("bottomMenuCode", Integer.parseInt(String.valueOf(paramMap.get("bottomMenuKey"))));
			
	
		checkFavorite.put("loginMemberNo", loginMemberNo);
		
		
		
		int favoriteCheck = mapper.getFavorite(checkFavorite);
		
		List<BottomMenu> bottomMenuList = mapper.bottomTopMenu();
		
		String bottomMenuName = null;
		
		for(int i = 0; i < bottomMenuList.size(); i++) {
			

			if(mapper.getBottomName(Integer.parseInt(String.valueOf(paramMap.get("bottomMenuKey")))) == bottomMenuList.get(i).getBottomMenuCode()) {
				bottomMenuName = bottomMenuList.get(i).getBottomMenuName();
				break;

			}
		}
		
		mapList.put("bottomMenuName", bottomMenuName);
		mapList.put("pagination", pagination);
		mapList.put("boardList", boardList);
		mapList.put("favoriteCheck", favoriteCheck);
		mapList.put("bottomMenuList", bottomMenuList);
		
		return mapList;
	}
	
	
	
	
	
	
	
	
}
