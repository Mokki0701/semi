package edu.kh.gowith.myPage.model.service;

import java.lang.reflect.Member;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.myPage.model.dto.MyPage;

public interface MyPageService {

	int updateInfo(MyPage inputMember);

	int deleteMember(String currentPw, int memberNo);

	

	

}
