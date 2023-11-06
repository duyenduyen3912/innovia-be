package com.datn.product;

public class Product {
	private String name, description, category, tag, long_description, weight, size, image;
	private int id, price;
	
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}


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


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getLong_description() {
		return long_description;
	}


	public void setLong_description(String long_description) {
		this.long_description = long_description;
	}


	public String getWeight() {
		return weight;
	}


	public void setWeight(String weight) {
		this.weight = weight;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public Product(String name, String description, String category, String tag, String long_description, String weight,
			String size, String image, int id, int price) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.tag = tag;
		this.long_description = long_description;
		this.weight = weight;
		this.size = size;
		this.image = image;
		this.id = id;
		this.price = price;
	}




	
	
}
