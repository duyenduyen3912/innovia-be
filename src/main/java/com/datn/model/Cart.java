package com.datn.model;

public class Cart {
	private  String note;
	private int idproduct, quantity,iduser;
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public Cart(String note, int idproduct, int quantity, int iduser) {
		super();
		this.note = note;
		this.idproduct = idproduct;
		this.quantity = quantity;
		this.iduser = iduser;
	}
	
	
	
}
