package com.datn.model;

public class Review {
	private int idproduct, star, iduser;
	private String comment;
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Review(int idproduct, int star, String comment, int iduser) {
		super();
		this.idproduct = idproduct;
		this.star = star;
		this.iduser = iduser;
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

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	
	
	
}
