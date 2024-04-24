package edu.kh.gowith.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberMenu {

	private int memberNo;
	private String bottomMenuCode;
	private String favorite;
	
	private String bottomMenuName;
	
	
}
