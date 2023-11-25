package com.datn.response;

import java.util.List;

import com.datn.model.PCart;

public class CartResponse {
	private String status;
	private List<PCart> data;
	public CartResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartResponse(String status, List<PCart> data) {
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
	public List<PCart> getData() {
		return data;
	}
	public void setData(List<PCart> data) {
		this.data = data;
	}
	
}
