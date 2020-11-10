package com.bitcamp.myapp.register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class RegisterController {
	//로그인폼 이동
	@RequestMapping("/login")
	public String login() {
		
		return "register/login";
	}
	
	//로그인 구현
	@RequestMapping(value = "/loginOk", method=RequestMethod.POST)
	public String loginOk(RegisterVO vo, HttpServletRequest request) {
		
		RegisterDAO dao = new RegisterDAO();
		RegisterVO resultVo= dao.login(vo);
		
		HttpSession session = request.getSession();
		
		if(resultVo==null) {
			return "register/login";
		}else {
			session.setAttribute("logId", resultVo.getUserid());
			session.setAttribute("logName", resultVo.getUsername());
			session.setAttribute("logStatus", "Y");
			return "home";
		}
	}
	
	//로그아웃 구현
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}
}
