package com.bitcamp.test.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	SqlSession sqlSession;
	public SqlSession getSqlSession() {
		return sqlSession;
	}
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	@RequestMapping("/boardList")
	public ModelAndView boardList() {
		BoardDaoImp dao = sqlSession.getMapper(BoardDaoImp.class);
		List<BoardVO> list = dao.boardAllRecord(); //이거 관련 select문 인터페이스에 쓸때 writedate를 to_char로 변환해서 가져왔는데 이때 주의할점은 별명이 있어야 한다는것.
													
													//dao에서 이름으로 셀렉트 하는데 별명을 안쓰면 이름이 함수로 나와서 못찾음
													//인터페이스에 받을값은 List인데 xml에서 결과값은 VO로 들어가야한다. <--이거주의. resultType=경로를 VO로
													//List에 뭘 담을지를 적어줘야한다고 생각하면 쉽다. VO를 담으니까 VO경로를 쓴다.
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("board/list");
		return mav;
	}
	@RequestMapping("/boardWrite")
	public String boardWrite() {
		return "board/boardWrite";
	}
	@RequestMapping(value="/boardWriteOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(BoardVO vo, HttpServletRequest request, HttpSession session) {
		vo.setIp(request.getRemoteAddr());
		vo.setUserid((String)session.getAttribute("userid"));
											//여기서 xml insert문 설정할때 매개변수 설정하려면 parameterType="com.bitcamp.test.board.BoardVO" 이렇게하면된다.
		BoardDaoImp dao = sqlSession.getMapper(BoardDaoImp.class);
		int result = dao.boardInsert(vo);
		ModelAndView mav = new ModelAndView();
		if(result>0) {
			mav.setViewName("redirect:boardList");
		}else {
			mav.addObject("msg", "글쓰기");
			mav.setViewName("board/result");
		}
		return mav;
	}
	@RequestMapping("/boardView")
	public ModelAndView boardView(int no) {
		
									//리턴이 VO이고 매개변수가 int 인 dao를 xml에서 작성할때 이렇게 한다. parameterType="int" resultType="com.bitcamp.text.board.BoardVO
									//select no, subject, content, userid, hit, writedate from freeboard where no=#{param1} 여기서 param1은 위처럼 parameterType를 int나 String등으로 설정했을때
									// 그 안에 있는 매개변수 순서중에 첫번째것이라는게 param1이다. 2번째면 param2다. 쭉 이어짐.
		BoardDaoImp dao = sqlSession.getMapper(BoardDaoImp.class);
		dao.hitCount(no); //조회수증가
		BoardVO vo = dao.boardSelect(no);
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo",	vo);
		mav.setViewName("board/view");
		return mav;
	}
	@RequestMapping("/boardEdit")
	public ModelAndView boardEdit(int no) {
		BoardDaoImp dao = sqlSession.getMapper(BoardDaoImp.class);
		BoardVO vo = dao.boardSelect(no);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", vo);
		mav.setViewName("board/edit");
		return mav;
	}
	@RequestMapping(value="/editOk", method=RequestMethod.POST)
	public ModelAndView editOk(BoardVO vo, HttpSession session) {
		vo.setUserid((String)session.getAttribute("userid"));
		BoardDaoImp dao = sqlSession.getMapper(BoardDaoImp.class);
		int result = dao.boardUpdate(vo);
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {
			mav.addObject("no", vo.getNo());
			mav.setViewName("redirect:boardView");
		}else {
			mav.addObject("msg", "글수정");
			mav.setViewName("board/result");
		}
		return mav;
	}
	@RequestMapping("/boardDel")
	public ModelAndView boardDel(int no, HttpSession session) {
		BoardDaoImp dao = sqlSession.getMapper(BoardDaoImp.class);
		int result = dao.boardDelete(no, (String)session.getAttribute("userid"));
		ModelAndView mav = new ModelAndView();
		if(result>0) {
			mav.setViewName("redirect:boardList");
		}else {
			mav.addObject("msg", "글삭제");
			mav.setViewName("board/result");
		}
		//xml에서 insert, update, delete 시에 parameterType은 optional 이다. 생략 가능하다.
		
		
		return mav;
	}
	
}
