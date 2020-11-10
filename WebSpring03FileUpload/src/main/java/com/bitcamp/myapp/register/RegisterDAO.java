package com.bitcamp.myapp.register;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bitcamp.myapp.Constants;

public class RegisterDAO {
	public JdbcTemplate template=null;
	
	public RegisterDAO() {
		this.template = Constants.template;
	}
	
	public RegisterVO login(RegisterVO vo) {
		///////////////////카운트 안해도 예외처리 하면 된다. 그건 밑에 써봄
		String sql = "select count(userid) cnt from register where userid=? and userpwd=?";
		RegisterVO vo1 = template.queryForObject(sql, new BeanPropertyRowMapper<RegisterVO>(RegisterVO.class), vo.getUserid(), vo.getUserpwd());
		
		if(vo1.getCnt()<=0) {
			return null;
		}else {
			String sql1 = "select userid, username from register where userid=? and userpwd=?";
			return template.queryForObject(sql1, new BeanPropertyRowMapper<RegisterVO>(RegisterVO.class), vo.getUserid(), vo.getUserpwd());
		}
		
	}
	
	///이게 위에서처럼 카운트 안하고 예외처리로 로그인 만든거다.
	public RegisterVO loginCheck(RegisterVO vo) {
		RegisterVO resultVO = null;
		try {
			String sql ="select userid, username from register where userid=? and userpwd=?";
			resultVO = template.queryForObject(sql, new BeanPropertyRowMapper<RegisterVO>(RegisterVO.class), vo.getUserid(), vo.getUserpwd());
			resultVO.setLogStatus("Y");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resultVO;
	}
}
