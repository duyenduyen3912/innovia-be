package com.datn.response;



public class ApiObjectResponse {
	private String status;
    private String description;
    private Object data; // List để lưu trữ dữ liệu, có thể là danh sách các đối tượng hoặc bất kỳ kiểu dữ liệu nào khác
	public ApiObjectResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApiObjectResponse(String status, String description, Object data) {
		super();
		this.status = status;
		this.description = description;
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
    
}
