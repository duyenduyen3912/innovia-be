package com.datn.response;

import java.util.List;

public class ApiResponse<T> {
    private String status;
    private String description;
    private List<T> data; // List để lưu trữ dữ liệu, có thể là danh sách các đối tượng hoặc bất kỳ kiểu dữ liệu nào khác

    public ApiResponse(String status, String description, List<T> data) {
        this.status = status;
        this.description = description;
        this.data = data;
    }

    // Các phương thức getter và setter

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
