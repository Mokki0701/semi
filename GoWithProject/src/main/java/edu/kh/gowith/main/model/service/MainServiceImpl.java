package edu.kh.gowith.main.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.gowith.board.model.dto.Board;
import edu.kh.gowith.main.model.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{
	
	private final MainMapper mapper; 
	
	
	// 좋아요 갯수 순서로 조회
	@Override
	public List<Board> popLike(String value) {
		
		
		return mapper.popLike();
	}

	// comment 갯수 순서로 조회
	@Override
	public List<Board> popComment(String value) {
		
		return mapper.popComment();
	}
	
	
	
}
