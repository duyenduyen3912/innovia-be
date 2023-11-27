package com.datn.model;

public class OrderList {
	private String id, date, status;
	private int total;
	public OrderList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderList(String id, String date, String status, int total) {
		super();
		this.id = id;
		this.date = date;
		this.status = status;
		this.total = total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
