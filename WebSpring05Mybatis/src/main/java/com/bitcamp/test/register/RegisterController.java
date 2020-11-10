package com.bitcamp.test.register;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
	SqlSession sqlSession; //변수를 선언하고 게터세터 생성
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}
	@Autowired //이 객체이름이랑 똑같은것을 servlet-context.xml에서 찾은 후에 여기에 대입해준다. 그러면 이 컨트롤러에선 sqlSession을 사용가능하다.
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@RequestMapping("/login")
	public String login() {
		return "register/login";
	}
	
	//sqlSession은 sqlSessionFactory로 만들고, sqlSessionFactory는 xml파일을 가지고 만든다. 
	//dao를 알려주면 매핑되어있는 xml파일을 찾아가서 그 안에있는 쿼리문을 실행한다.
	@RequestMapping(value="/loginOk", method=RequestMethod.POST)
	public ModelAndView loginOk(RegisterVO vo, HttpSession session) {
		//			매개변수에 추상클래스를 넣어줘야한다... .class로 클래스라고 알려줘야한다.
		RegisterDaoImp dao = sqlSession.getMapper(RegisterDaoImp.class);
		//이렇게 dao를 생성한다.
		
		RegisterVO resultVO = dao.login(vo); //추상클래스 안의 login메소드를 실행하면 xml에있던 select id=login이 실행된다.
		//만약 이걸 실행했는데 선택된 레코드가 없으면 null이 돌아온다.
		//로그인 유무는 resultVO가 null인지 아닌지로 확인하면된다.
		ModelAndView mav = new ModelAndView();
		if(resultVO == null) { //로그인 실패
			mav.setViewName("redirect:/login");
		}else {
			session.setAttribute("userid", resultVO.getUserid());
			session.setAttribute("username", resultVO.getUsername());
			session.setAttribute("logStatus", "Y");
			mav.setViewName("redirect:/");
		}
		return mav;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
}
