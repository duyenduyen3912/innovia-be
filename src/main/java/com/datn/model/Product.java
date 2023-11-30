package com.datn.model;

public class Product {
	private String name, description, category, tag, long_description, weight, size, image, category_name, model;
	private int id, price;
	private float star;
	
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Product(String name, String description, String category, String tag, String long_description, String weight,
			String size, String image, int id, int price, float star, String category_name, String model) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.tag = tag;
		this.long_description = long_description;
		this.weight = weight;
		this.size = size;
		this.image = image;
		this.category_name = category_name;
		this.model = model;
		this.id = id;
		this.price = price;
		this.star = star;
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


	public float getStar() {
		return star;
	}


	public void setStar(float star) {
		this.star = star;
	}


	public String getCategory_name() {
		return category_name;
	}


	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}

	
	
	
}
