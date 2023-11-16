package com.datn.response;

import java.util.List;

import com.datn.product.Product;

public class SearchImageResponse {
	private String status;
	private int total_products, total_pages, index_page;
	private List<Product> data;
	private List<Product> recommend_product;
	public SearchImageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SearchImageResponse(String status, int total_products, int total_pages, int index_page, List<Product> data,
			List<Product> recommend_product) {
		super();
		this.status = status;
		this.total_products = total_products;
		this.total_pages = total_pages;
		this.index_page = index_page;
		this.data = data;
		this.recommend_product = recommend_product;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotal_products() {
		return total_products;
	}
	public void setTotal_products(int total_products) {
		this.total_products = total_products;
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
	public List<Product> getData() {
		return data;
	}
	public void setData(List<Product> data) {
		this.data = data;
	}
	public List<Product> getRecommend_product() {
		return recommend_product;
	}
	public void setRecommend_product(List<Product> recommend_product) {
		this.recommend_product = recommend_product;
	}
	
	
	
}
