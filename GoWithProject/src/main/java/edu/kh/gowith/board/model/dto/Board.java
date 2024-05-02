package edu.kh.gowith.board.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private String boardUpdateDate;
	private int readCount;
	private String boardDelFl;
	
	private int memberNo;
	private int bottomBoardCode;
	
	private String boardNotification;
	private int boardLikeCount;
	
	private int topMenuCode;
	
	private String bottomMenuName;
	private int commentCount;
		
	private List<BoardImg> imgList;
	private List<Comment> commentList;
	
	private String memberNickname;
	private int memberRank;
	private String profileImg;
	
	private int nextBoardNo;
	private int preBoardNo;
	
	private int checkCommentDate;
	
	// 메인페이지 인기 게시글 상세 조회용
	private int bottomMenuCode;
	
	private String imgRename;
	
	
}
