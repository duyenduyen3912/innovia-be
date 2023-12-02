package com.datn.rcmsys;

public class ProductRcm {
	private String name, description, category;
	private int price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public ProductRcm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductRcm(String name, String description, String category, int price) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
	}
	
}
