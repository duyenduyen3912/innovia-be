package com.datn.response;

import com.datn.user.User;

public class ResponseLogin {
	private String status, jwt, data;
	private User user;
	public ResponseLogin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseLogin(String status, String jwt, String data, User user) {
		super();
		this.status = status;
		this.jwt = jwt;
		this.data = data;
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
