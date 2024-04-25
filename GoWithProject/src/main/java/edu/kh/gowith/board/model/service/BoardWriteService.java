package edu.kh.gowith.board.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.board.model.dto.Board;

public interface BoardWriteService {

	//게시글 작성 
	int boardInsert(Board inputBoard, List<MultipartFile> images);

}
