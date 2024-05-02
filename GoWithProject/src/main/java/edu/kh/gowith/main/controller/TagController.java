package edu.kh.gowith.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.gowith.main.model.dto.Tag;
import edu.kh.gowith.main.model.service.TagService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("tag")
public class TagController {

	private final TagService service;
	
	@GetMapping("select")
	@ResponseBody
	private List<Tag> tagList(){
		
		return service.tagList();
	}
	
}
