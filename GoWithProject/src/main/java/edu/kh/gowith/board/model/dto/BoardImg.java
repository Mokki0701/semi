package edu.kh.gowith.board.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardImg {
	
	private int imgNumber;
	private String imgPath;
	private String imgOriginalName;
	private String imgRename;
	private int imgOrder;
	private int topMenuCode;
	private int bottomMenuCode;
	private int boardNo;
	
	//게시글 이미지 삽입/수정 시 사용
	private MultipartFile uploadFile;
	
}
