package edu.kh.gowith.board.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.gowith.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper;
	
	@Override
	public String boardFavorite(Map<String, String> paramMap) {
	
		return mapper.boardFavortie(paramMap);
	}
	
}
