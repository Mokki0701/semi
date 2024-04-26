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
	
	// 인기글 비동기 조회
	@Override
		public List<Board> popBoardInquiry(String value) {
		
			return mapper.popBoardInquiry(value);
		}

}
