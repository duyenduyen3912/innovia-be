package com.datn.product;

public class HighestBook {
	private String name, author, detail,image;
	private int idBook,rating;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getIdBook() {
		return idBook;
	}
	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public HighestBook(String name, String author, String detail, int idBook, String image, int rating) {
		super();
		this.name = name;
		this.author = author;
		this.detail = detail;
		this.idBook = idBook;
		this.rating = rating;
		this.image = image;
	}
	public HighestBook() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
