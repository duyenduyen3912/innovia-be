package com.datn.model;

public class Order {
	private int iduser, totalmoney;
	private String idproduct, phone, address;
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(int iduser, int totalmoney, String idproduct, String phone, String address) {
		super();
		this.iduser = iduser;
		this.totalmoney = totalmoney;
		this.idproduct = idproduct;
		this.phone = phone;
		this.address = address;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public int getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(int totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(String idproduct) {
		this.idproduct = idproduct;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
