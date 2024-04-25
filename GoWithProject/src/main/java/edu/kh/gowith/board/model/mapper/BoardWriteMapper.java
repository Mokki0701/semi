package edu.kh.gowith.board.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.Board;

@Mapper
public interface BoardWriteMapper {

	// 게시글 작성
	int boardInsert(Board inputBoard);

}
