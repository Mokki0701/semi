package edu.kh.gowith.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.MemberMenu;


@Mapper
public interface BoardMapper {

	int boardFavortie(Map<String, String> paramMap);

	List<Board> getBoardList(int bottomMenuCode);

	int getListCount(int bottomMenuCode);

	List<Board> getBoardList(int bottomMenuCode, RowBounds rowBounds);

	MemberMenu getFavorite(Map<String, Integer> checkFavorite);

}
