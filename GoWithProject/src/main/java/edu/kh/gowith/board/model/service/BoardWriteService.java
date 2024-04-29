package edu.kh.gowith.board.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.TopMenu;

public interface BoardWriteService  {

	//게시글 작성 
	int boardInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException;

	//게시판 제목 
	String selectTitle(int topMenuCode);

	//관리자 공지글 등록
	int notiInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException;

	//상위 메뉴 밑에 하위 메뉴 코드들 얻어오기
	List<BottomMenu> selectBottomCodes(int topMenuCode);

	//상위 메뉴 리스트
	List<TopMenu> topMenuCodeList();




}
