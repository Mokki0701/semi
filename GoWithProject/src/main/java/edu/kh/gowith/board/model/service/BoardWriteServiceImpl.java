package edu.kh.gowith.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.board.model.dto.BoardImg;
import edu.kh.gowith.board.model.dto.BottomMenu;
import edu.kh.gowith.board.model.dto.TopMenu;
import edu.kh.gowith.board.model.exception.BoardInsertException;
import edu.kh.gowith.board.model.exception.ImageDeleteException;
import edu.kh.gowith.board.model.exception.ImageUpdateException;
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
	
	
	//관리자 게시글 등록
	@Override
	public int notiInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		// 1. INSERT 결과로 작성된 게시글 번호(생성된 시퀀스 번호) 반환 받기
		
		int result = mapper.notiInsert(inputBoard);

		// 삽입 실패시
		if (result == 0)
			return 0;

		// 삽입된 게시글의 번호를 변수로 저장
		int boardNo = inputBoard.getBoardNo();
				
		// 이미지 List
		List<BoardImg> imageList = new ArrayList<>();

		for (int i = 0; i < images.size(); i++) {

			// 파일이 존재하는 경우
			if (!images.get(i).isEmpty()) {

				// 원본명
				String originalName = images.get(i).getOriginalFilename();
				// 변경명
				String rename = Utility.fileRename(originalName);

				// 모든 값을 저장한 DTO 생성
				BoardImg img = BoardImg.builder().imgOriginalName(originalName).imgRename(rename).imgPath(webPath)
						.bottomMenuCode(inputBoard.getBottomBoardCode()).topMenuCode(inputBoard.getTopMenuCode())
						.boardNo(boardNo).imgOrder(i).uploadFile(images.get(i)).build();

				imageList.add(img);
			}
		} // for문

		if (imageList.isEmpty()) {
			return boardNo;
		}

		// 여러행 삽입하는 sql 호출
		result = mapper.insertImg(imageList);

		// 다중 삽입 구문 성공확인
		if (result == imageList.size()) {
			// 서버에 파일 저장하기
			for (BoardImg img : imageList) {
				img.getUploadFile().transferTo(new File(folderPath + img.getImgRename()));
			}
		} else {
			// 부분 삽입 실패 == 전체 서비스 실패
			// -> 이전 삽입 내용 모두 롤백 -> RuntimeException 강제 발생 시켜서 롤백 해주기
			throw new BoardInsertException();
		}

		return boardNo;

	}
	
	// 게시글 하위 메뉴코드 목록 조회
	@Override
	public List<BottomMenu> selectBottomCodes(int topMenuCode) {
		return mapper.selectBottomCodes(topMenuCode);
	}
	
	//상위 메뉴 리스트 반환
	@Override
	public List<TopMenu> topMenuCodeList() {
		return mapper.topMenuCodeList();
	}
	
	
	// 게시글 수정 시 세팅할 제목, 내용
	@Override
	public Board searchBoard(int boardNo) {
		return mapper.searchBoard(boardNo);
	}
	
	// 수정 페이지에 표시될 미리보기 이미지
	@Override
	public List<BoardImg> imgList(int boardNo) {
		return mapper.imgList(boardNo);
	}
	
	// 공지사항 수정
	@Override
	public int notiUpdate(Board inputBoard, List<MultipartFile> images,String deleteOrder) throws IllegalStateException, IOException {
		
		int result = mapper.notiUpdate(inputBoard);
		
		if(result == 0) {
			return 0;
		}
		
		// 기존에 존재 -> 삭제된 이미지
		if(deleteOrder != null && !deleteOrder.equals("")) {
			Map<String, Object> map = new HashMap<>();
			map.put("deleteOrder", deleteOrder);
			map.put("boardNo", inputBoard.getBoardNo());
			
			result = mapper.deleteImg(map);
			
			if(result == 0) {
				throw new ImageDeleteException();
			}
		}
		
		//3. 선택한 파일이 존재할 경우 해당 파일 정보만 모아두는 List
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
						       topMenuCode(inputBoard.getTopMenuCode()).boardNo(inputBoard.getBoardNo()).imgOrder(i).uploadFile(images.get(i)).build();
				
				imageList.add(img);
				
				//4. 업로드 하려는 이미지 정보(img)를 이용해 수정 또는 삽입 수행
				result = mapper.updateImg(img);
				
				if(result == 0) {
					// 수정 실패 == 기존 해동 순서 (imgOrder)에 이미지 없었음
					// -> 삽입 수행
					
					result = mapper.insertimage(img);
				}
			}
			
			//수정 또는 삭제 실패한 경우
			if(result == 0) {
				throw new ImageUpdateException(); //예외 발생 -> 롤백
			}
			
		}//for문
		
		//선택한 파일이 없을 경우
		
		if(imageList.isEmpty()) {
			return result;
		}
		
		// 수정, 삭제된 이미지 파일을 서버에 저장하겠음
		
		// 서버에 파일 저장하기
		for (BoardImg img : imageList) {
			img.getUploadFile().transferTo(new File(folderPath + img.getImgRename()));
		}
		
		return result;
	}
	


}
