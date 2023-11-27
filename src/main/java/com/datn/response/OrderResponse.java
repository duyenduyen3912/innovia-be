package com.datn.response;
import java.util.List;

import com.datn.model.OrderItem;

public class OrderResponse {
		private String id;
		private int total;
		private List<OrderItem> product;
		public OrderResponse() {
			super();
			// TODO Auto-generated constructor stub
		}
		public OrderResponse(String id, int total, List<OrderItem> product) {
			super();
			this.id = id;
			this.total = total;
			this.product = product;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public List<OrderItem> getProduct() {
			return product;
		}
		public void setProduct(List<OrderItem> product) {
			this.product = product;
		}
		
		
}
