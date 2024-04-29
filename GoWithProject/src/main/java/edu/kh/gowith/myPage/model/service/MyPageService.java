package edu.kh.gowith.myPage.model.service;



import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.myPage.model.dto.MyPage;


public interface MyPageService {

	
	//회원탈퇴
	int deleteMember(String currentPw, int memberNo);



	//정보수정
	int updateInfo(Member inputMember, String[] memberAddress, String newPw, MultipartFile uploadImg,int statusCheck) throws IllegalStateException, IOException;

	
	
	
}


	


