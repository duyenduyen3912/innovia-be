package com.datn.response;

import java.util.List;

public class StringResponse {
	private String status;
	private List<String> data;
	public StringResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StringResponse(String status, List<String> data) {
		super();
		this.status = status;
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	
	
}
