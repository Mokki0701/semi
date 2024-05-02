package edu.kh.gowith.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.gowith.main.model.dto.Tag;

@Mapper
public interface TagMapper {

	List<Tag> tagList(RowBounds rowBounds);



}
