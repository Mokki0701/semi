package edu.kh.gowith.board.model.dto;

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
	private int readCount;
	private String boardDelFl;
	
	private int memberNo;
	private int bottomBoardCode;
	
	private String boardNotification;
	private int boardLikeCount;
	
	private int topMenuCode;
	
	private String bottomMenuName;
	
}
