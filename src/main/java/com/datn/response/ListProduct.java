package com.datn.response;

import java.util.List;
import com.datn.product.Product;

public class ListProduct {
	private List<Product> product;
	private List<Product> recommend;
	public ListProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListProduct(List<Product> product, List<Product> recommend) {
		super();
		this.product = product;
		this.recommend = recommend;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	public List<Product> getRecommend() {
		return recommend;
	}
	public void setRecommend(List<Product> recommend) {
		this.recommend = recommend;
	}
	
	
}
