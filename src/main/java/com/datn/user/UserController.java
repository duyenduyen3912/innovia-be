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

import com.datn.response.Response;
import com.thi.bt.user.User;

import org.apache.commons.io.IOUtils;

//@Controller
@RestController
@CrossOrigin

public class UserController {
	
	private UserDAO UserDAO = new UserDAO();
	 private static final int PAGE_SIZE = 9;
	 
	 @RequestMapping(value = "/login", method = RequestMethod.POST,
             produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response addUser(Model model, Response res, @RequestBody User user, @PathVariable String username) throws IOException
	{				
			String mess = bookDAO.insertUser(user);
			
			if(mess.equals("error")) {
				model.addAttribute("errorMessageName","Name đã tồn tại");
				 
				res.setResponse("error");
				
			}
			else {
				res.setResponse("success");
			}
			
			return res;

	}   

	
}
