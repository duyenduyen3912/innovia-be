package com.datn.product;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.datn.model.Product;
import com.datn.model.Review;
import com.datn.response.DataResponse;
import com.datn.response.ListProduct;
import com.datn.response.Response;
import com.datn.response.ReviewResponse;
import com.datn.response.SearchImageResponse;
import com.datn.response.StringResponse;
import com.datn.response.DivideData;


import org.apache.commons.io.IOUtils;

//@Controller
@RestController
@CrossOrigin

public class ProductController {
	
	private ProductDAO productDAO = new ProductDAO();
	private static final int PAGE_SIZE = 9;
	DivideData div = new DivideData(); 
	 
	    @GetMapping("/products")
    public Object getProducts(@RequestParam(name = "page", required = false, defaultValue = "1") int currentPage) throws IOException {
    	try {
            List<Product> allProducts = productDAO.selectAllProducts();

            Response response = div.Divide(allProducts, PAGE_SIZE, currentPage, "success");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/DProduct")
	public DataResponse getproduct(@RequestParam(name = "id", required = false)int id) throws IOException{
    	Product product = productDAO.selectProduct(id);

        if (product != null) {
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            DataResponse res = new DataResponse("success", productList);
            return res;
        } else {
            return new DataResponse("error", null);
        }
	}
	
    @GetMapping("/Category")
	public StringResponse getCategory() throws IOException{
    	List<String> category = new ArrayList<>();
    	category = productDAO.selectCategory();
    	StringResponse res = null;
    	if(category.size() != 0) {
    		res = new StringResponse("success", category);
    	} else res = new StringResponse("success", category);
    	return res;
	}
    
    @GetMapping("/GetProductByCategory")
	public Object getPoductByCategory(
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name= "page", required = false) int currentPage
	) throws IOException{
    	Response response = null;
    	List<Product> allProducts = new ArrayList();
    	allProducts = productDAO.selectAllProductByCategory(category);
    	if(allProducts.size() != 0) {
    		response = div.Divide(allProducts, PAGE_SIZE, currentPage, "category");
    	} else response = new Response("faild", 0, 0, 0, allProducts);
         
        return ResponseEntity.ok(response);
	}
	 

	@RequestMapping(value = "/search", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object searchByImage(@RequestBody String data, @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage) throws IOException {
		
		ListProduct result_product = null;
		List<Product> allProducts = new ArrayList<>();
		Response response = null;
		Boolean isSearchByName = false;
		try {
			if(data.length() > 200) {
				
				result_product = productDAO.selectAllProductByImage(data);
				allProducts = result_product.getProduct();
			}
			else {
				String keyword = data.substring(1, data.length() - 1);
				allProducts = productDAO.selectAllProductByName(keyword);
				isSearchByName = true;
			}
			if(allProducts.size() != 0) {
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
				
				if(isSearchByName) {
					return new Response("success", totalProducts, totalPages, currentPage, productsForPage);
				} else {
					return new SearchImageResponse("success", totalProducts, totalPages, currentPage, productsForPage, result_product.getRecommend());
				}
			} else {
				if(isSearchByName) {
					return new Response("success", 0, 0, 0, allProducts);
				} else {
					return new SearchImageResponse("success", 0, 0, 0, allProducts, result_product.getRecommend());
				}
				
			}
        } catch (Exception e) {
     
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
	}
	
	@PostMapping("/addReview")
	@ResponseBody
	public String addReview(@RequestBody Review r) throws IOException {
		String result = productDAO.insertReview(r);
		return result;
	}
	
	@GetMapping("/getReview")
	public Object getReview (
			@RequestParam (name = "idproduct", required = false) int idproduct,
			@RequestParam (name = "page", required = false, defaultValue = "1") int currentPage
	) {
		ReviewResponse response = null;
		List<Review> r = productDAO.getReviewOfProduct(idproduct);
		if(r.size() != 0) {
			response = div.DivideReviews(r, 5, currentPage, "success");
		} else {
			response = div.DivideReviews(r, 5, currentPage, "faild");
		}
		return response;
	}
	
	
	


	
}
