package com.datn.response;

import java.util.List;

import com.datn.model.BestSelling;

public class BestSellingProductResponse {
	private String status;
	private List<BestSelling> data;
	public BestSellingProductResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BestSellingProductResponse(String status, List<BestSelling> data) {
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
	public List<BestSelling> getData() {
		return data;
	}
	public void setData(List<BestSelling> data) {
		this.data = data;
	}
	
}
