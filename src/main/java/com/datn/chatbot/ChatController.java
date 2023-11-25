package com.datn.chatbot;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.datn.model.Chat;

@RestController
@CrossOrigin
public class ChatController {
	private ChatDAO chatDAO = new ChatDAO();
	
	@PostMapping("/chat-answer")
	@ResponseBody
	public Chat ChatAnswer(@RequestBody String question) throws IOException {
		System.out.println(question);
		String question_final = null;
		if(question.contains("=")) {
			question_final = question.replaceAll("^=+|=+$", "");
		} else question_final = question;
		System.out.println(question_final);
		Chat res = null;
		try {
			res = chatDAO.selectAnswer(question_final);
			return res;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
}
