package com.bitcamp.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//로그인 유무 인터셉터. HandlerInterceptorAdapter상속받기.. 메소드들 오버라이드 해야한다.
public class LoginInterceptor extends HandlerInterceptorAdapter {

	//컨트롤러가 호출되기전에 실행된다.(컨트롤러 메소드 실행 직전에 수행됩니다. true 를 반환하면 계속 진행이 되고  false 를 리턴하면 실행 체인(다른 인터셉터, 컨트롤러 실행)이 중지되고 반환 됩니다.  필터의 응답 처리가 있다면 그것은 실행이 됩니다.)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인 여부를 확인하여 로그인 된 경우 계속 실행하고 로그인이 안된경우 컨트롤러 실행을 중지한다.
		HttpSession ses = request.getSession();
		
		String logStatus = (String)ses.getAttribute("logStatus");
		//로그인이 안된경우 로그인 폼으로 보냄.. 현재 진행을 중단시킨다.
		if(logStatus == null || !logStatus.equals("Y")) {
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		
		
		return true;
	}
	//컨트롤러가 실행한후 view페이지로 이동하기 전에 호출된다.(컨트롤러 메소드 실행 직후에 실행 됩니다. View 페이지가 렌더링 되기전에   ModelAndView 객체를 조작 할 수 있습니다.)

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	//컨트롤러가 실행한후 호출되는 메소드(view까지 실행이 끝났을때..   View 페이지가 렌더링 되고 난 후 에 실행됩니다.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	//Servlet 3.0 부터 비동기 요청이 가능해 졌습니다.   비동기 요청시 postHandle와 afterCompletion 은 실행되지 않고, 이 메소드가 실행됩니다.
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	
////////////아래는 잘못써진것같음 
	
	//컨트롤러가 호출되기전에 실행된다. ..
//	public boolean preHandler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
//		//로그인 여부를 확인하여 로그인 된 경우 계속 실행하고 로그인이 안된경우 컨트롤러 실행을 중지한다.
//		HttpSession ses = request.getSession();
//		
//		String logStatus = (String)ses.getAttribute("logStatus");
//		//로그인이 안된경우 로그인 폼으로 보냄.. 현재 진행을 중단시킨다.
//		if(logStatus == null || !logStatus.equals("Y")) {
//			response.sendRedirect(request.getContextPath()+"/login");
//			return false;
//		}
//		
//		
//		return true;
//	}
	
//	//컨트롤러가 실행한후 view페이지로 이동하기 전에 호출된다.
//	public void postHandler(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception{
//		
//	}
//	
//	//컨트롤러가 실행한후 호출되는 메소드(view까지 실행이 끝났을때)
//	public void afterHadler(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//		
//	}
}
