package com.datn.response;
import java.util.List;

import com.datn.model.OrderList;
public class OrderListResponse {
	private String status;
	private List<OrderList> data;
	public OrderListResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderListResponse(String status, List<OrderList> data) {
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
	public List<OrderList> getData() {
		return data;
	}
	public void setData(List<OrderList> data) {
		this.data = data;
	}
	
	
}
