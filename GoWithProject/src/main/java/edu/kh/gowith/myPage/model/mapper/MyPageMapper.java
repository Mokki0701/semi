package edu.kh.gowith.myPage.model.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.myPage.model.dto.MyPage;



@Mapper
public interface MyPageMapper {

    // 암호화된 비밀번호 조회
    String selectPw(int memberNo);

    // 회원 탈퇴
    int deleteMember(int memberNo);


    //프로필이미지변경
	int profile(MyPage mem);


	//정보수정
	int updateInfo(Member inputMember);





}