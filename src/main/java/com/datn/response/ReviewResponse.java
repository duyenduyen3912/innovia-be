package com.datn.response;

import java.util.List;

import com.datn.model.Review;

public class ReviewResponse {
	private String status;
	private int total_pages, index_page, total_reviews;
	private List<Review> data;
	public ReviewResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReviewResponse(String status, int total_pages, int index_page, int total_reviews, List<Review> data) {
		super();
		this.status = status;
		this.total_pages = total_pages;
		this.index_page = index_page;
		this.total_reviews = total_reviews;
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotal_pages() {
		return total_pages;
	}
	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}
	public int getIndex_page() {
		return index_page;
	}
	public void setIndex_page(int index_page) {
		this.index_page = index_page;
	}
	public int getTotal_reviews() {
		return total_reviews;
	}
	public void setTotal_reviews(int total_reviews) {
		this.total_reviews = total_reviews;
	}
	public List<Review> getData() {
		return data;
	}
	public void setData(List<Review> data) {
		this.data = data;
	}
	
	
}
