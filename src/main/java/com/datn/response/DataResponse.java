package com.datn.response;

import java.util.List;

import com.datn.product.Product;

public class DataResponse {
    private String status;
    private List<Product> data; // Sử dụng kiểu List<Product> thay vì List data

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Product> getData() { // Sử dụng kiểu List<Product> thay vì List getData()
        return data;
    }

    public void setData(List<Product> data) { // Sử dụng kiểu List<Product> thay vì List setData()
        this.data = data;
    }

    public DataResponse(String status, List<Product> data) { // Sử dụng kiểu List<Product> thay vì List data
        this.status = status;
        this.data = data;
    }

    public DataResponse() {
        // Hãy để constructor này trống nếu bạn không có logic nào cần thêm vào đây.
    }
}
