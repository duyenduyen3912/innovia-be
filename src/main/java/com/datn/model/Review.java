package com.datn.model;

public class Review {
	private int idproduct, star;
	private String comment;
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Review(int idproduct, int star, String comment) {
		super();
		this.idproduct = idproduct;
		this.star = star;
		this.comment = comment;
	}
	public int getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
