package com.datn.model;

public class PCart {
	private int iduser, idproduct, total_quantity, price, total_price;
	private String image, name;
	public PCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PCart(int iduser, int idproduct, int total_quantity, int price, int total_price, String image, String name) {
		super();
		this.iduser = iduser;
		this.idproduct = idproduct;
		this.total_quantity = total_quantity;
		this.price = price;
		this.total_price = total_price;
		this.image = image;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public int getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}
	public int getTotal_quantity() {
		return total_quantity;
	}
	public void setTotal_quantity(int total_quantity) {
		this.total_quantity = total_quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
