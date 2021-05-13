package com.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//Controller da main page
@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String hello() {

		return "home"; 
	}
}
