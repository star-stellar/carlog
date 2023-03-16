package com.jafa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("name", "홍길동");
		model.addAttribute("welcome","우리집");
		return "index";
	}
}