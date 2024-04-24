package edu.kh.gowith.member.model.dto;

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
public class MemberMenu2 {
	
	private int memberNo;
	private int bottomMenuCode;
	private char favorite;

}
