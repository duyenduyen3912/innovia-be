package com.datn.chatbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.datn.model.Chat;

public class ChatDAO {
	private String jdbcURL = "jdbc:mysql://192.168.1.24:3306/datn";
	private String jdbcUsername = "duyenduyen";
	private String jdbcPassword = "tothichmeou39";
	
	private static final String GET_ANSWER_BY_OPTIONS = "SELECT answer, action, image FROM chat where option_id = ?";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		}catch (SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public Chat selectAnswer (String option_id) {
		Chat c = new Chat();
		try( Connection connection = getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWER_BY_OPTIONS);
			preparedStatement.setString(1, option_id);
			ResultSet res = preparedStatement.executeQuery();
			if(res.next()) {
				c.setAnswer(res.getString("answer"));
				c.setAction(res.getString("action"));
				c.setImage(res.getString("image"));
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
}
