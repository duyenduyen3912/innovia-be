package com.datn.product;


import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.datn.model.BestSelling;
import com.datn.model.Cart;
import com.datn.model.Id;
import com.datn.model.Order;
import com.datn.model.OrderList;
import com.datn.model.PCart;
import com.datn.model.Product;
import com.datn.model.Review;
import com.datn.rcmsys.SearchService;
import com.datn.security.*;
import com.datn.response.ApiObjectResponse;
import com.datn.response.ApiResponse;
import com.datn.response.ListProduct;
import com.datn.response.OrderResponse;
import com.datn.response.Response;
import com.datn.response.ReviewResponse;
import com.datn.response.SearchImageResponse;

import io.jsonwebtoken.Claims;

import com.datn.response.DivideData;

@RestController
@CrossOrigin

public class ProductController {
	
	private ProductDAO productDAO = new ProductDAO();
	private Jwt jwt = new Jwt();
	private static final int PAGE_SIZE = 9;
	DivideData div = new DivideData(); 
	@Autowired
    private SearchService searchService;
	
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
	public ApiResponse getproduct(@RequestParam(name = "id", required = false)int id) throws IOException{
    	Product product = productDAO.selectProduct(id);

        if (product != null) {
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            return new ApiResponse("success", "Lấy thông tin sản phẩm thành công", productList);
           
        } else {
        	return new ApiResponse("falied", "Lấy thông tin sản phẩm không thành công", null);
        }
	}
	
    @GetMapping("/Category")
	public ApiResponse getCategory() throws IOException{
    	List<String> category = new ArrayList<>();
    	category = productDAO.selectCategory();
    	if(category.size() != 0) {
    		return new ApiResponse("success", "Lấy thông tin thành công", category);
    	} else return new ApiResponse("falied", "Lấy thông tin không thành công", null);
   
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
				System.out.println(data);
				List<String> rcm = searchService.suggestProducts("products", data);
				System.out.println(rcm);
				allProducts = productDAO.selectAllProductByName(rcm);
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
		Map<String, String>  res = new HashMap<>();
		if(r.size() != 0) {
			return response = div.DivideReviews(r, 5, currentPage, "success");
		} else {
			res.put("status", "failed");
			return res;
		}
	
	}
	
	@PostMapping("/addToCart")
	@ResponseBody
	public Map<String, String> addToCart( @RequestBody Cart c, @RequestHeader("Authorization") String authorizationHeader) throws IOException{
		
		Map<String, String> response = new HashMap<>();
		if(jwt.isValidJwt(authorizationHeader)) {
			Claims claims = jwt.decodeJwtToken(authorizationHeader);
			if(claims.getSubject().equals(String.valueOf(c.getIduser()))) {
				String result = productDAO.AddProductToCart(c);
				if(result.equals("error")) {
			        response.put("status", "failed");
			        response.put("data", "Có lỗi xảy ra, vui lòng thử lại sau");
			        
				} else {
					response.put("status", "success");
			        response.put("data", "Thêm sản phẩm thành công");
			     
				}
			} else {
				response.put("status", "failed");
		        response.put("data", "Lỗi xác minh thông tin");
			}
		} else {
			response.put("status", "ExpiredToken");
	        response.put("description", "Hết phiên đăng nhập");
		}
		
		return response;
	}
	
	@PostMapping("/selectCart")
	@ResponseBody
	public ApiResponse selectCart( @RequestBody String id, @RequestHeader("Authorization") String authorizationHeader) throws IOException{
		Map<String, String> response = new HashMap<>();
		if(jwt.isValidJwt(authorizationHeader)) {
			Claims claims = jwt.decodeJwtToken(authorizationHeader);
			if(claims.getSubject().equals(id)) {
				int iduser = Integer.parseInt(id);
				List<PCart> res = productDAO.selectProductInCart(iduser);
				if(res.size() != 0) {
					return new ApiResponse("success", "Lấy dữ liệu thành công", res);
				} else return new ApiResponse("failed", "Lấy dữ liệu không thành công", res);
			} else {
		        return new ApiResponse("failed", "Lỗi xác minh thông tin", null);
			}
		} else {
	        return new ApiResponse("ExpiredToken", "Hết phiên đăng nhập", null);
		}
				
	}
	
	@PostMapping("/updateCart")
	@ResponseBody
	public Map<String, String> updateCart( @RequestBody Cart c, @RequestHeader("Authorization") String authorizationHeader) throws IOException{
		Map<String, String> response = new HashMap<>();
		if(jwt.isValidJwt(authorizationHeader)) {
			Claims claims = jwt.decodeJwtToken(authorizationHeader);
			if(claims.getSubject().equals(String.valueOf(c.getIduser()))) {
				String res = productDAO.UpdateCart(c);
				if(res.equals("error")) {
					response.put("status", "failed");
			        response.put("data", "Có lỗi xảy ra, vui lòng thử lại sau");
			        return response;
				} else {
					response.put("status", "success");
			        response.put("data", "Cập nhật thành công");
			        return response;
				}
			} else {
				response.put("status", "failed");
		        response.put("data", "Lỗi xác minh thông tin");
		        return response;
			}
		} else {
			response.put("status", "ExpiredToken");
	        response.put("data", "Hết hạn phiên đăng nhập");
	        return response;
		}
				
	}
	
	@PostMapping("/deleteCart")
	@ResponseBody
	public Map<String, String> deleteCart( @RequestBody Cart c, @RequestHeader("Authorization") String authorizationHeader) throws IOException{
		Map<String, String> response = new HashMap<>();
		if(jwt.isValidJwt(authorizationHeader)) {
			Claims claims = jwt.decodeJwtToken(authorizationHeader);
			if(claims.getSubject().equals(String.valueOf(c.getIduser()))) {
				String res = productDAO.DeleteCart(c);
				if(res.equals("error")) {
					response.put("status", "failed");
			        response.put("data", "Có lỗi xảy ra, vui lòng thử lại sau");
			        return response;
				} else {
					response.put("status", "success");
			        response.put("data", "Cập nhật thành công");
			        return response;
				}
			} else {
				response.put("status", "failed");
		        response.put("data", "Lỗi xác minh thông tin");
		        return response;
			}
		} else {
			response.put("status", "ExpiredToken");
	        response.put("data", "Hết hạn phiên đăng nhập");
	        return response;
		}
				
	}
	
	@PostMapping("/order")
	@ResponseBody
	public Map<String, String> order( @RequestBody Order o, @RequestHeader("Authorization") String authorizationHeader) throws IOException{
		Map<String, String> response = new HashMap<>();
		if(jwt.isValidJwt(authorizationHeader)) {
			Claims claims = jwt.decodeJwtToken(authorizationHeader);
			if(claims.getSubject().equals(String.valueOf(o.getIduser()))) {
				String result = productDAO.Order(o);
				if(result.equals("error")) {
			        response.put("status", "failed");
			        response.put("data", "Có lỗi xảy ra, vui lòng thử lại sau");
			        return response;
			        
				} else {
					response.put("status", "success");
			        response.put("data", "Đặt hàng thành công");
			        return response;
				}
			} else {
				response.put("status", "failed");
		        response.put("data", "Lỗi xác minh thông tin");
		        return response;
			}
		} else {
			response.put("status", "ExpiredToken");
	        response.put("data", "Hết hạn phiên đăng nhập");
	        return response;
		}	
	}
	
	@PostMapping("/selectOrderList")
	@ResponseBody
	public ApiResponse orderList( @RequestBody String iduser,  @RequestHeader("Authorization") String authorizationHeader) throws IOException{
		if(jwt.isValidJwt(authorizationHeader)) {
	
			Claims claims = jwt.decodeJwtToken(authorizationHeader);
			if(claims.getSubject().equals(iduser)) {
				int id = Integer.parseInt(iduser);
				List<OrderList> result = productDAO.selectOrders(id);
				if(result.size() != 0) {
					return new ApiResponse("success", "Lấy danh sách thành công", result);
				} else {
					return new ApiResponse("failed", "Có lỗi xảy ra, vui lòng thử lại sau", result);
				}
			} else {
				return new ApiResponse("failed", "Lỗi xác minh thông tin", null);
			}
		} else return new ApiResponse("ExpiredToken", "Hết phiên đăng nhập", null);
	}
	
	@GetMapping("/selectBestSellingProduct")
	public ApiResponse bestSelling() throws IOException{
		List<BestSelling> result = productDAO.selectBestSellingProduct();
		if(result.size() != 0) {
			return new ApiResponse("success", "Lấy danh sách thành công", result);
		} else {
			return new ApiResponse("failed", "Lấy thông tin không thành công", null);
		}
	}
	
	@PostMapping("/selectOrderItem")
	@ResponseBody
	public ApiObjectResponse selectOrderItem (@RequestBody Id i, @RequestHeader("Authorization") String authorizationHeader) throws IOException {
		if(jwt.isValidJwt(authorizationHeader)) {
			Claims claims = jwt.decodeJwtToken(authorizationHeader);
			if(claims.getSubject().equals(i.getAuth())) {
				OrderResponse result = productDAO.selectOrder(i.getId());
				return new ApiObjectResponse("success", "Lấy thông tin thành công", result);
			} else {
				return new ApiObjectResponse("failed", "Lấy thông không tin thành công", null);
			}
		} else {
			return new ApiObjectResponse("ExpiredToken", "Hết phiên đăng nhập", null);
		}
	}

	@PostMapping("/cancelOrder")
	@ResponseBody
	public Map<String, String> cancelOrder( @RequestBody Id i, @RequestHeader("Authorization") String authorizationHeader) throws IOException{
		Map<String, String> response = new HashMap<>();
		if(jwt.isValidJwt(authorizationHeader)) {
			Claims claims = jwt.decodeJwtToken(authorizationHeader);
			if(claims.getSubject().equals(i.getAuth())) {
				String res = productDAO.CancelOrder(i);
				if(res.equals("error")) {
					response.put("status", "failed");
			        response.put("data", "Có lỗi xảy ra, vui lòng thử lại sau");
			        return response;
				} else {
					response.put("status", "success");
			        response.put("data", "Cập nhật thành công");
			        return response;
				}
			} else {
				response.put("status", "failed");
		        response.put("data", "Lỗi xác minh thông tin");
		        return response;
			}
		} else {
			response.put("status", "ExpiredToken");
	        response.put("data", "Hết hạn phiên đăng nhập");
	        return response;
		}
				
	}
	
}
