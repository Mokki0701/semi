package edu.kh.gowith.board.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

	String boardFavortie(Map<String, String> paramMap);

}
