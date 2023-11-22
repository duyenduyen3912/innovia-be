package com.datn.model;

public class Chat {
	private String answer, action, image;

	public Chat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Chat(String answer, String action, String image) {
		super();
		this.answer = answer;
		this.action = action;
		this.image = image;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
