package edu.kh.gowith.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member loginMember(String memberEmail);

}
