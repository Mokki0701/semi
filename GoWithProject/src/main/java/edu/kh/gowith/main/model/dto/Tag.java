package edu.kh.gowith.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

	private int tagNo;
	private String tagName;
	
	// ************* 혹시 모를 태그 게시판을 위한 준비물
	private int boardNo;
	private int commentNo;
	
}
