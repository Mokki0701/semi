package edu.kh.gowith.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.Comment;
import edu.kh.gowith.board.model.dto.TopMenu;


@Mapper
public interface BoardMapper {

	int boardFavortie(Map<String, String> paramMap);

	List<Board> getBoardList(int bottomMenuCode);

	int getListCount(int bottomMenuCode);

	List<Board> getBoardList(int bottomMenuCode, RowBounds rowBounds);

	

	List<TopMenu> bottomTopMenu();

	int getSearchCount(Map<String, Object> paramMap);

	List<Board> getSearchList(Map<String, Object> paramMap, RowBounds rowBounds);

	int getFavorite(Map<String, Integer> favoriteCheck);

	int getBottomName(int object);

	int boardInsertFavorite(Map<String, String> paramMap);

	List<BottomMenu> selectBottomList(int topMenuCode);

	Board boardDetail(Map<String, Object> paramMap);

	int increaseReadCount(int boardNo);

	int resultReadCount(int boardNo);

	String bottomMenuName(int bottomMenuCode);
	
	List<Comment> commentList(Map<String, Object> paramMap);

}
