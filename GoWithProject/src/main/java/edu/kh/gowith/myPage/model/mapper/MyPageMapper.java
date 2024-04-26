package edu.kh.gowith.myPage.model.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.gowith.myPage.model.dto.MyPage;

@Mapper
public interface MyPageMapper {

    // 암호화된 비밀번호 조회
    String selectPw(int memberNo);

    // 회원 탈퇴
    int deleteMember(int memberNo);

    // 정보 수정
    int updateInfo(MyPage inputMember);

    // 프로필 정보 업데이트
    int profile(MyPage mem);

    // 비밀번호 변경
	int changePw(Map<String, Object> pack);

	

}