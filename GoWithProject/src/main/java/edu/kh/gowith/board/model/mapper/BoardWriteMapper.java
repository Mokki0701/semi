package edu.kh.gowith.board.model.mapper;

import java.util.List;
import java.util.Map;

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

	// 수정할 게시판 찾아 제목, 내용 가져오기
	Board searchBoard(int boardNo);

	// 수정 페이지에 표시될 미리보기 이미지
	List<BoardImg> imgList(int boardNo);

	// 공지사항 수정
	int notiUpdate(Board inputBoard);

	// 이미지 삭제
	int deleteImg(Map<String, Object> map);

	// 이미지 수정
	int updateImg(BoardImg img);
	
	// 게시글 이미지 삽입 (1행 삽입)
	int insertimage(BoardImg img);

	

}
