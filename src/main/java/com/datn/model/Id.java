package com.datn.model;

public class Id {
	private String id, auth;
	public Id() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Id(String id, String auth) {
		super();
		this.id = id;
		this.auth = auth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
	
}
