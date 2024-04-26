package edu.kh.gowith.myPage.model.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.gowith.common.config.Utility;
import edu.kh.gowith.myPage.model.dto.MyPage;
import edu.kh.gowith.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
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
	
	
	
	
	@Override
	public int updateInfo(MyPage inputMember, 
			String newPw,
			String currentPw,
			int memberNo, 
			MultipartFile profileImg,
			MyPage loginMember,
			String[] memberAddress
			) throws IllegalStateException, IOException{
		
		String encPw = mapper.selectPw(memberNo);
		String updatePath = null;
		String rename = null;
		
		if(inputMember.getMemberAddress().equals(",,")) {
			inputMember.setMemberAddress(null);
		}else {
			String address =String.join("^^^",memberAddress);
			inputMember.setMemberAddress(address);
		}
		
		if(!bcrypt.matches(currentPw,encPw)) {
			return 0 ;
		}else{
			String pw = bcrypt.encode(newPw);
			Map<String,Object> pack = new HashMap<>();
			
			pack.put("memberNo",memberNo);
			pack.put("pw", pw);	
			
			int pwChangeResult = mapper.changePw(pack);
			
			if(pwChangeResult ==0) {
				return 0;
			}	
		}
		
		
		if (!profileImg.isEmpty()) {
			rename = Utility.fileRename(profileImg.getOriginalFilename());
			updatePath = profileWebPath + rename;
		
		
			MyPage mem = MyPage.builder().memberNo(loginMember.getMemberNo())
							.profileImg(updatePath)
							.build();  
	
			
			int profileUpdateResult = mapper.profile(mem);
			if(profileUpdateResult >0) {
				profileImg.transferTo(new File(profileFolderPath + rename));
				loginMember.setProfileImg(updatePath);
			}else {
				return 0;
			}
		}
	
	return mapper.updateInfo(inputMember);
}

	
	
	
	

	
	
	
}
