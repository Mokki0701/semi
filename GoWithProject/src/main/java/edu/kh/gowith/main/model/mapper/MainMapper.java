package edu.kh.gowith.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.Board;

@Mapper
public interface MainMapper {


	// 좋아요 갯수 순으로 조회
	List<Board> popLike();

	// comment 갯수 순서로 조회
	List<Board> popComment();

}
