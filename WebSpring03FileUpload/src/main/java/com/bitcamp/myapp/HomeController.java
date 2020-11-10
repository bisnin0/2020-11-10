package com.bitcamp.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	public JdbcTemplate Template;
	public JdbcTemplate getTemplate() {
		return Template;
	}
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		Template = template;
		Constants.template=template; //Constants가 static이라 가능
	}


	@RequestMapping("/")
	public String home() {
		
		return "home";
	}
	
}
