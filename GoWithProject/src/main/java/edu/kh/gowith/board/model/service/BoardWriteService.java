package edu.kh.gowith.board.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.board.model.dto.Board;

public interface BoardWriteService  {

	//게시글 작성 
	int boardInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException;

	//게시판 제목 
	String selectTitle(int topMenuCode);

	//관리자 공지글 등록
	int notiInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException;



}
