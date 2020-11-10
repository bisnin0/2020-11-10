package com.bitcamp.ajaxtest.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	@RequestMapping("/ajaxHome")
	public String startAjax() {
		return "ajax/ajaxView";
	}
	
															//Ajax는 web.xml에 한글인코딩 설정을 해도 한글이 깨진다. 그래서 여기서 설정해야한다.
	@RequestMapping(value="/ajaxString", method=RequestMethod.GET, produces="application/text;charset=UTF-8") 
	@ResponseBody 											//ajax 매핑 메소드는 view페이지가 리턴되지 않는다는걸 ResponseBody어노테이션을 사용해서 알려야한다.
	public String startString(int no, String username, String userid) { //이렇게만쓰면 받는다. //VO를 안쓰면 이방식으로 받는다. 변수이름은 보낼때 사용한 변수이름과 같아야한다.
		System.out.println("클라이언트가 서버로 보낸 데이터 -->"+no+","+username+","+userid);
		String txt = "번호:"+no+"<br/>이름:"+username+"<br/>아이디:"+userid+"<br/>";
		
		return txt;
	}
	
	@RequestMapping("/ajaxObject") //한글 데이터 안보낼때는 produces 안써도 된다.
	@ResponseBody
	public TestVO ajaxObject() {
		return new TestVO(5555,"세종대왕","서울시 중구");
	}
	
	@RequestMapping("/ajaxList") //이것도 한글 안깨지고 된다. ajax에서 원래는 한글 깨져야하는데 왜이런지는 확인필요. 예외라고 보면 될듯
	//@RequestMapping(value="/ajaxList", method=RequestMethod.GET, produces="application/json;charset=UTF-8;") //text를 json으로 바꿔야 에러가 안났다. 받는데이터가 json이 아닌데 왜?
	@ResponseBody
	public List<TestVO> ajaxList(TestVO vo){
		List<TestVO> list = new ArrayList<TestVO>(); //원래는 DB조회해서 담으면 되는데 DAO안만들었고 그냥 보냈으니까 이렇게
		
		list.add(vo);
		list.add(new TestVO(1000, "홍길동", "서울시 서대문구")); //VO 생성자 이용해서 데이터 추가
		list.add(new TestVO(2000, "강감찬", "서울시 종로구")); //VO 생성자 이용해서 데이터 추가
		list.add(new TestVO(3000, "세종대왕", "서울시 영등포구")); //데이터 추가
		list.add(new TestVO(4000, "김구", "서울시 중구")); //데이터 추가
		
		return list;
	}
	
	@RequestMapping("/ajaxMap")
	@ResponseBody
	public HashMap<String, TestVO> ajaxMap() {
		HashMap<String, TestVO> map = new HashMap<String, TestVO>();
		
		map.put("k1", new TestVO(1111, "이민호", "서울시 송파구"));
		map.put("k2", new TestVO(2222, "이재훈", "서울시 종로구"));
		map.put("k3", new TestVO(3333, "박서준", "서울시 중구"));
		map.put("k4", new TestVO(4444, "조 바이든", "미국 워싱턴DC"));
		
		return map;
	}
	
	//@RequestMapping("/ajaxJson")
	@RequestMapping(value="/ajaxJson", method=RequestMethod.GET, produces="application/text;charset=UTF-8;")
	@ResponseBody
	public String ajaxJson() {
		//		{"no":"1234", "username":"홍길동", "tel":"010-7777-8888", "addr":"서울시 동작구}  json형식. 자바입장에선 문자열임
		
		int no = 1234;
		String username = "홍길동";
		String tel = "010-7777-8888";
		String addr = "서울시 동작구";
						// \" 는 닫는따옴표가 아니라는뜻
		String jsonText = "{\"no\":\""+no+"\", \"username\":\""+username+"\", \"tel\":\""+tel+"\", \"addr\":\""+addr+"\"}";
		System.out.println(jsonText);
		return jsonText;
	}
}
