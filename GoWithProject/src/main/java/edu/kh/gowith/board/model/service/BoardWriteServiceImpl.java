package edu.kh.gowith.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.mapper.BoardWriteMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardWriteServiceImpl implements BoardWriteService{

	public final BoardWriteMapper mapper;
	
	// 게시글 작성
	@Override
	public int boardInsert(Board inputBoard, List<MultipartFile> images) {
		
		// 1. INSERT 결과로 작성된 게시글 번호(생성된 시퀀스 번호) 반환 받기
		int result = mapper.boardInsert(inputBoard);
		
		
		return 0;
	}

}
