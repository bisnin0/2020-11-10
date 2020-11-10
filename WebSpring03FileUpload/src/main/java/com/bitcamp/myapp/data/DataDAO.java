package com.bitcamp.myapp.data;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bitcamp.myapp.Constants;

public class DataDAO {
	public JdbcTemplate template=null;
	
	public DataDAO() {
		this.template=Constants.template;
	}
	
	public List<DataVO> allList(){
		String sql = "select no, title, userid, filename1, filename2 from data order by no desc";
		return template.query(sql, new BeanPropertyRowMapper<DataVO>(DataVO.class));
	}
	
	public int dataInsert(DataVO vo) {
		int result = 0;
		try { //이름을 길게해서 에러가 생기면 여기서 발생하기때문에 예외처리 해야한다. 안하면 그냥 에러라서 업로드 멈춘다.
			String sql ="insert into data(no, title, content, userid, ip, filename1, filename2) "
					+ " values(a_sq.nextval, ?, ?, ?, ?, ?, ?)";
			result = template.update(sql, vo.getTitle(), vo.getContent(), vo.getUserid(), vo.getIp(), vo.getFilename1(), vo.getFilename2());
		}catch(Exception e) {e.getMessage();}
		return result;
	}
}
