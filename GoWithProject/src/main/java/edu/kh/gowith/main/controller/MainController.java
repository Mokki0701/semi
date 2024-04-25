package edu.kh.gowith.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	@RequestMapping("") // "/" 요청 매핑, 모든 메서드 요청 받아내기(get post 구분 x)
	public String mainPage() {
		
		return "common/index";
	}
	
	
	
}

