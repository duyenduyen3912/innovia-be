package com.datn.response;

import java.util.List;

import com.datn.model.Product;
import com.datn.model.Review;
import com.datn.response.Response;

public class DivideData {
	public Response Divide (List<Product> allProducts, int PAGE_SIZE, int currentPage, String status) {
		int totalProducts = allProducts.size();

        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        int startIndex = (currentPage - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalProducts);

        List<Product> productsForPage = allProducts.subList(startIndex, endIndex);

        Response response = new Response(status, totalProducts, totalPages, currentPage, productsForPage);
        return response;
	}
	
	public ReviewResponse DivideReviews (List<Review> allReviews, int PAGE_SIZE, int currentPage, String status) {
		int totalReviews = allReviews.size();

        int totalPages = (int) Math.ceil((double) totalReviews / PAGE_SIZE);

        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        int startIndex = (currentPage - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalReviews);

        List<Review> reviewsForPage = allReviews.subList(startIndex, endIndex);

        ReviewResponse response = new ReviewResponse(status, totalPages, currentPage, totalReviews, reviewsForPage);
        return response;
	}
	
}
