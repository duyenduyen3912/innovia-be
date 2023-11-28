package com.datn.user;

public class UpdateUser {
	private String phone, password, fullname;
	private String iduser;
	public UpdateUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public UpdateUser(String phone, String password, String fullname, String iduser) {
		super();
		this.phone = phone;
		this.password = password;
		this.fullname = fullname;
		this.iduser = iduser;
	}



	public String getIduser() {
		return iduser;
	}



	public void setIduser(String iduser) {
		this.iduser = iduser;
	}



	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
