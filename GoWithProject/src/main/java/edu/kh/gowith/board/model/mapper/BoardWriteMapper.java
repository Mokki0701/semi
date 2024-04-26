package edu.kh.gowith.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BoardImg;

@Mapper
public interface BoardWriteMapper {

	// 게시글 작성
	int boardInsert(Board inputBoard);

	// 이미지 삽입하기
	int insertImg(List<BoardImg> imageList);

	// 게시판 제목
	String selectTitle(int topMenuCode);

	

}
