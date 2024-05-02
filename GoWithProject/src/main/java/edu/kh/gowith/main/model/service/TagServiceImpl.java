package edu.kh.gowith.main.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.gowith.main.model.dto.Tag;
import edu.kh.gowith.main.model.mapper.TagMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagMapper mapper;
	
	@Override
	public List<Tag> tagList() {
		
		RowBounds rowBounds = new RowBounds(0, 5);
		
		return mapper.tagList(rowBounds);
	}
	
}
