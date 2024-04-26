package edu.kh.gowith.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BoardImg;
import edu.kh.gowith.board.model.exception.BoardInsertException;
import edu.kh.gowith.board.model.mapper.BoardWriteMapper;
import edu.kh.gowith.common.config.Utility;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor=Exception.class)
@RequiredArgsConstructor
public class BoardWriteServiceImpl implements BoardWriteService{

	public final BoardWriteMapper mapper;
	
	@Value("${my.board.web-path}")
	private String webPath;
	
	@Value("${my.board.folder-path}")
	private String folderPath;
	
	
	// 게시글 작성
	@Override
	public int boardInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		// 1. INSERT 결과로 작성된 게시글 번호(생성된 시퀀스 번호) 반환 받기
		int result = mapper.boardInsert(inputBoard);
		
		// 삽입 실패시
		if(result == 0) return 0;
		
		// 삽입된 게시글의 번호를 변수로 저장
		int boardNo = inputBoard.getBoardNo();
		
		// 이미지 List
		List<BoardImg> imageList = new ArrayList<>();
		
		for(int i=0 ; i<images.size(); i++) {
			
			//파일이 존재하는 경우
			if(!images.get(i).isEmpty()) {
				
				//원본명
				String originalName = images.get(i).getOriginalFilename();
				//변경명
				String rename = Utility.fileRename(originalName);
				
				//모든 값을 저장한 DTO 생성
				BoardImg img = BoardImg.builder().imgOriginalName(originalName).imgRename(rename)
						       .imgPath(webPath).bottomMenuCode(inputBoard.getBottomBoardCode()).
						       topMenuCode(inputBoard.getTopMenuCode()).boardNo(boardNo).imgOrder(i).uploadFile(images.get(i)).build();
				
				imageList.add(img);
			}
		}//for문
		
		if(imageList.isEmpty()) {
			return boardNo;
		}
		
		//여러행 삽입하는 sql 호출
		result = mapper.insertImg(imageList);
		
		//다중 삽입 구문 성공확인
		if(result == imageList.size()) {
			//서버에 파일 저장하기
			for(BoardImg img : imageList) {
				img.getUploadFile().transferTo(new File(folderPath + img.getImgRename()));
			}
		}else {
			// 부분 삽입 실패 == 전체 서비스 실패
			// -> 이전 삽입 내용 모두 롤백 -> RuntimeException 강제 발생 시켜서 롤백 해주기
			throw new BoardInsertException();
		}
		
			
		return boardNo;
	}
	
	//게시판 제목
	@Override
	public String selectTitle(int topMenuCode) {
		return mapper.selectTitle(topMenuCode);
	}
	
	
	
	
	
	
	

}
