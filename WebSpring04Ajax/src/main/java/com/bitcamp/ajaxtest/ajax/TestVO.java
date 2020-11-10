package com.bitcamp.ajaxtest.ajax;

public class TestVO {
	private int no;
	private String username;
	private String addr;
	
	public TestVO() {}
	public TestVO(int no, String username, String addr) {
		this.no = no;
		this.username = username;
		this.addr = addr;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getaddr() {
		return addr;
	}
	public void setaddr(String addr) {
		this.addr = addr;
	}
	
}
