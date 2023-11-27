package com.datn.model;

public class BestSelling {
	private String name, image;
	private int price;
	public BestSelling() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BestSelling(String name, String image, int price) {
		super();
		this.name = name;
		this.image = image;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
