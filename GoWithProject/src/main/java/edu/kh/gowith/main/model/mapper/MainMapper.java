package edu.kh.gowith.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.Board;

@Mapper
public interface MainMapper {

	// 메인페이지 보드 목록 조회
	List<Board> popBoardInquiry(String value);

}
