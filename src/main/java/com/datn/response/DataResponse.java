package com.datn.response;

import java.util.List;

import com.datn.product.Product;

public class DataResponse {
    private String status;
    private List<Product> data; 

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Product> getData() { 
        return data;
    }

    public void setData(List<Product> data) { 
        this.data = data;
    }

    public DataResponse(String status, List<Product> data) { 
        this.status = status;
        this.data = data;
    }

    public DataResponse() {
        // Hãy để constructor này trống nếu bạn không có logic nào cần thêm vào đây.
    }
}
