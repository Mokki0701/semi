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
public class BottomMenu {

	private int bottomMenuCode;
	private String bottomMenuName; 
	private int topMenuCode;
	
	private int memberNo;
	
}
