package com.datn.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datn.response.ResponseLogin;
import com.datn.user.*;


import org.apache.commons.io.IOUtils;

//@Controller
@RestController
@CrossOrigin

public class UserController {
	
	private UserDAO userDAO = new UserDAO();

	 
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
				result = new ResponseLogin("success", "jwt", "Đăng ký thành công", u);
			}
			return result;

	}
	 
	 @PostMapping("/login")
	 @ResponseBody
	 public ResponseLogin login(@RequestBody Login l) throws IOException {
		 ResponseLogin result = null;
		 User res = userDAO.selectUser(l);
		 if(res.getUsername() != null) {
			 result = new ResponseLogin("success", "jwt", "Thông tin chính xác", res);
		 } else {
			 result = new ResponseLogin("failed", "jwt", "Thông tin đăng nhập không chính xác", res);
		 }
		 
		 return result;
	 }

	
}
