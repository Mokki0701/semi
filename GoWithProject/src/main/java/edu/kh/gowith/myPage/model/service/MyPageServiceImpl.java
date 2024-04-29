package edu.kh.gowith.myPage.model.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.common.config.Utility;
import edu.kh.gowith.member.model.dto.Member;
import edu.kh.gowith.myPage.model.dto.MyPage;
import edu.kh.gowith.myPage.model.mapper.MyPageMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService{

	private final MyPageMapper mapper;
	private final BCryptPasswordEncoder bcrypt;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath;
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	
	
	//회원탈퇴
	@Override
	public int deleteMember(String currentPw, int memberNo) {
		
		String encPw = mapper.selectPw(memberNo);
		
		int result = 0;
		
		if(bcrypt.matches(currentPw,encPw)) {
			result = mapper.deleteMember(memberNo);
		}
		return result;
	}
	
	
	
	

	//정보수정
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress, String newPw,  MultipartFile uploadImg,int statusCheck) throws IllegalStateException, IOException{
		
		
		//주소데이터 가공
		if( inputMember.getMemberAddress().equals(",,") ) {
			inputMember.setMemberAddress(null);
		} else { 
			String address = String.join("^^^", memberAddress);
			inputMember.setMemberAddress(address);
		}
		
		//새비밀번호 암호화
		if(newPw.length() > 0) {
			String pw = bcrypt.encode(newPw);
			inputMember.setMemberPw(pw);
		}
		
		String rename = null;
		String updatePath = null;
		
		// 업로드한 이미지가 있을 경우
		if( !uploadImg.isEmpty() ) {
			
			// updatePath 조합
			
			// 파일명 변경
			rename = Utility.fileRename(uploadImg.getOriginalFilename());
			
			//   /myPage/profile/변경된파일명.jpg
			updatePath = profileWebPath + rename;
			
			
			inputMember.setProfileImg(updatePath);
		}
			
		
		if(statusCheck==0) {
			inputMember.setProfileImg("default");
		}
		
		int result = mapper.updateInfo(inputMember);
		
		
		if(result>0) {
			// 프로필 이미지를 없앤 경우(NULL로 수정한 경우)를 제외
			// -> 업로드한 이미지가 있을 경우
			if( !uploadImg.isEmpty() ) {
				// 파일을 서버 지정된 폴더에 저장
				uploadImg.transferTo(new File(profileFolderPath + rename));
			}
						
						
		}
	
		// SQL 수행 후 결과 반환
		return result;
	}
		


	
	
	
}
