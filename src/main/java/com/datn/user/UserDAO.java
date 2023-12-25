package com.datn.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class UserDAO {
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
		private String jdbcURL = "jdbc:mysql://192.168.1.24:3306/datn";
		private String jdbcUsername = "duyenduyen";
		private String jdbcPassword = "tothichmeou39";
		
		
		private static final String SELECT_USER_BY_USERNAME ="select * from users where username= ?";
		public static final String ADD_NEW_USER = "insert into users (username, password, fullname, email, phone) values (?,?,?,?,?) ";
		private static final String UPDATE_USER = "UPDATE users SET password = ?, fullname = ?  , phone = ? WHERE id = ?;";
		public UserDAO() {
			
		}
		
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

		public User selectUser(Login l) {
			User u = new User();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME);
				preparedStatement.setString(1,l.getUsername());
				ResultSet result = preparedStatement.executeQuery();
				if(result.next())
				{
						u.setId(result.getInt("id"));
						u.setUsername(result.getString("username"));
						u.setPassword(result.getString("password"));
						u.setEmail(result.getString("email"));
						u.setFullname(result.getString("fullname"));
						u.setPhone(result.getString("phone"));
						
						if(l.getPassword().equals(u.getPassword())) {
							return u;
					}
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return u.createEmptyUser();
		}
		
		public String insertUser (User u) {
			try(Connection connection = getConnection()) {
				PreparedStatement ps2 = connection.prepareStatement(SELECT_USER_BY_USERNAME);
				ps2.setString(1,u.getUsername());
				ResultSet resultSet = ps2.executeQuery();
				if(resultSet.next()) {
					return "error";
				}
				PreparedStatement ps = connection.prepareStatement(ADD_NEW_USER);
				int result = 0;
			
				ps.setString(1, u.getUsername());
				ps.setString(2, u.getPassword());
				ps.setString(3, u.getEmail());
				ps.setString(4, u.getFullname());
				ps.setString(5, u.getPhone());
			
				result = ps.executeUpdate();

				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return "OK";
		
		}
		
		public String updateUser (UpdateUser u) {
			try(Connection connection = getConnection()) {
				PreparedStatement ps = connection.prepareStatement(UPDATE_USER);
				ps.setString(1, u.getPassword());
				ps.setString(2, u.getFullname());
				ps.setString(3, u.getPhone());
				ps.setInt(4, Integer.parseInt(u.getIduser()));
				int result = 0; 
				result = ps.executeUpdate();
				if(result > 0) {
					return "OK";
				}
			}  catch(Exception e) {
				e.printStackTrace();
			}
			return "error";
		}
		
		
		
		
		
	
}