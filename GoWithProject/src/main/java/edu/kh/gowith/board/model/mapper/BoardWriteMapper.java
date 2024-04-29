package edu.kh.gowith.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BoardImg;
import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.TopMenu;

@Mapper
public interface BoardWriteMapper {

	// 게시글 작성
	int boardInsert(Board inputBoard);

	// 이미지 삽입하기
	int insertImg(List<BoardImg> imageList);

	// 게시판 제목
	String selectTitle(int topMenuCode);

	// 공지 등록
	int notiInsert(Board inputBoard);

	// 하위 메뉴 코드 반환 받기
	List<BottomMenu> selectBottomCodes(int topMenuCode);

	// 탑 메뉴 리스트 반환
	List<TopMenu> topMenuCodeList();

	

}
