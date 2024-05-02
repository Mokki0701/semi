package edu.kh.gowith.main.model.dto;

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
public class SixString {
	
	private String imgRename;
	private String boardTitle;
	private String boardWriteDate;
	private String memberNickname;
	private String commentCount;
	
}
