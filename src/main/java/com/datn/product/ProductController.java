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

import com.datn.response.DataResponse;
import com.datn.response.Response;


import org.apache.commons.io.IOUtils;

//@Controller
@RestController
@CrossOrigin

public class ProductController {
	
	private ProductDAO productDAO = new ProductDAO();
	 private static final int PAGE_SIZE = 9;
	 
	    @GetMapping("/products")
	    public Object getProducts(@RequestParam(name = "page", required = false, defaultValue = "1") int currentPage) throws IOException {
	    	try {
	            List<Product> allProducts = productDAO.selectAllProducts();

	            // Calculate the total number of products
	            int totalProducts = allProducts.size();

	            // Calculate the total number of pages
	            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

	            // Ensure currentPage is within valid range
	            if (currentPage < 1) {
	                currentPage = 1;
	            } else if (currentPage > totalPages) {
	                currentPage = totalPages;
	            }

	            // Calculate the start index for the current page
	            int startIndex = (currentPage - 1) * PAGE_SIZE;

	            // Calculate the end index for the current page
	            int endIndex = Math.min(startIndex + PAGE_SIZE, totalProducts);

	            // Return only the products for the current page
	            List<Product> productsForPage = allProducts.subList(startIndex, endIndex);

	            // Create a response object containing the products and additional information
	            Response response = new Response("success", totalProducts, totalPages, currentPage, productsForPage);
	            
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
		 
	 
	 @GetMapping("/search")
	 public Object getproductSearch(
			 @RequestParam(name = "keyword", required = false) String keyword,
			 @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage
	) throws IOException{
		try {
			List<Product> allProducts = productDAO.selectAllProductByName(keyword);

            // Calculate the total number of products
            int totalProducts = allProducts.size();

            // Calculate the total number of pages
            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

            // Ensure currentPage is within valid range
            if (currentPage < 1) {
                currentPage = 1;
            } else if (currentPage > totalPages) {
                currentPage = totalPages;
            }

            // Calculate the start index for the current page
            int startIndex = (currentPage - 1) * PAGE_SIZE;

            // Calculate the end index for the current page
            int endIndex = Math.min(startIndex + PAGE_SIZE, totalProducts);

            // Return only the products for the current page
            List<Product> productsForPage = allProducts.subList(startIndex, endIndex);

            // Create a response object containing the products and additional information
            Response response = new Response("success", totalProducts, totalPages, currentPage, productsForPage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
     
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
	}
	 
	@RequestMapping(value = "/search-by-image", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String searchByImage(@RequestBody String image, @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage) {
	
		byte[] imageBytes = Base64.decodeBase64(image);
	        String filePath = "C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\image\\decode_image.jpg";

	        try {
	            File file = new File(filePath);
	            try (FileOutputStream fos = new FileOutputStream(file)) {
	                fos.write(imageBytes);
	            }

	            return "Tệp ảnh đã giải mã và lưu trữ tại: " + filePath;
	        } catch (IOException e) {
	            return "Lỗi khi lưu tệp ảnh đã giải mã: " + e.getMessage();
	        }
	    }
	
	 
	


	
}
