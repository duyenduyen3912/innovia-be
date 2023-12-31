package com.datn.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datn.response.ResponseLogin;
import com.datn.security.Jwt;
import com.datn.user.*;

import io.jsonwebtoken.Claims;

import org.apache.commons.io.IOUtils;

//@Controller
@RestController
@CrossOrigin

public class UserController {
	
	private UserDAO userDAO = new UserDAO();
	private Jwt jwt = new Jwt();
	 
	 @RequestMapping(value = "/signup", method = RequestMethod.POST,
             produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseLogin addUser(@RequestBody User u) throws IOException
	{		
		 	ResponseLogin result = null;
			String mess = userDAO.insertUser(u);
			if(mess.equals("error")) {
				result = new ResponseLogin("failed", "jwt", "Username đã tồn tại", u);
			} else {
				String token = jwt.generateJwtToken(String.valueOf(u.getId()));
				result = new ResponseLogin("success", token, "Đăng ký thành công", u);
			}
			return result;

	}
	 
	 @PostMapping("/login")
	 @ResponseBody
	 public ResponseLogin login(@RequestBody Login l) throws IOException {
		 ResponseLogin result = null;
		 User res = userDAO.selectUser(l);
		 if(res.getUsername() != null) {
			 String token = jwt.generateJwtToken(String.valueOf(res.getId()));
			 result = new ResponseLogin("success", token, "Thông tin chính xác", res);
		 } else {
			 result = new ResponseLogin("failed", null, "Thông tin đăng nhập không chính xác", res);
		 }
		 
		 return result;
	 }

	 @PostMapping("/updateUser")
	 @ResponseBody
	 public Map<String, String> updateUser(@RequestBody UpdateUser u, @RequestHeader("Authorization") String authorizationHeader) throws IOException {
		 Map<String, String> response = new HashMap<>();
		 System.out.println(u.getFullname());
		 System.out.println(u.getIduser());
		 System.out.println(u.getPassword());
		 System.out.println(u.getPhone());
		 if(jwt.isValidJwt(authorizationHeader)) {
				Claims claims = jwt.decodeJwtToken(authorizationHeader);
				if(claims.getSubject().equals(u.getIduser())) {
					String result = userDAO.updateUser(u);
					if(result.equals("error")) {
						response.put("status", "failed");
						response.put("data", "Có lỗi xảy ra, vui lòng thử lại sau");
					} else {
						response.put("status", "success");
						response.put("data", "Cập nhật thành công!");
					}
						
				} else {
					response.put("status", "failed");
			        response.put("data", "Lỗi xác minh thông tin");
				}
			} else {
				response.put("status", "ExpiredToken");
		        response.put("data", "Hết hạn phiên đăng nhập");
			}
		 return response;
	 }
	 
	 @GetMapping("/checkToken")
	 public Map<String, String> checkToken (@RequestHeader("Authorization") String authorizationHeader) throws IOException {
		 Map<String, String> response = new HashMap<>();
		 if(jwt.isValidJwt(authorizationHeader)) {
			 response.put("data", "true");
		
		 } else response.put("data", "false");
		 return response;
	 }
	
}
