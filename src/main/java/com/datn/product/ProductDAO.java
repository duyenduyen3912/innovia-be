package com.datn.product;

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



public class ProductDAO {
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
		private String jdbcURL = "jdbc:mysql://localhost:3306/datn";
		private String jdbcUsername = "root";
		private String jdbcPassword = "tothichmeou39";
		
		private static final String SELECT_ALL_Product = "select * from product";
		private static final String SELECT_ALL_ProductS_CATEGORY = "select * from product where category=?";
		private static final String SELECT_Product_BY_ID ="select * from product where id= ?";
		private static final String SELECT_Product_BY_NAME ="select * from product where name= ?";
		private static final String SELECT_CATEGORY = "select distinct category from Product";
		private static final String SEARCH_Product_BY_NAME ="select * from product where name like ?";
		private static final String RATE_CMT_SQL = "SELECT distinct name, author,detail,comment.idProduct, image,AVG(rate) as rating\r\n"
				+ "FROM Product, comment\r\n"
				+ "where Product.idProduct = comment.idProduct\r\n"
				+ "GROUP BY comment.idProduct\r\n"
				+ "ORDER BY rating DESC\r\n"
				+ "LIMIT 5;";
		public ProductDAO() {
			
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
		
		public List<Product> selectAllProducts(){
			List<Product> Products = new ArrayList<>();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_Product);
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					int idProduct = resultSet.getInt("id");
					String name = resultSet.getString("name");
					int price = resultSet.getInt("price");
					String description = resultSet.getString("description");
					String long_description = resultSet.getString("long_description");
					String category = resultSet.getString("category");
					String tag = resultSet.getString("tag");
					String weight = resultSet.getString("weight");
					String size = resultSet.getString("size");
					String image = resultSet.getString("image");
					Product b = new Product(name,description, category, tag, long_description, weight, size, image, idProduct, price);
					Products.add(b);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return Products;
		}
		
		
		
		
		
	
		
		public Product selectProduct(int idProduct) {
			Product aProduct = new Product();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_Product_BY_ID);
				preparedStatement.setInt(1,idProduct);
				ResultSet result = preparedStatement.executeQuery();
				while(result.next())
				{
					aProduct.setId(result.getInt("id"));
					aProduct.setName(result.getString("name"));
					aProduct.setDescription(result.getString("description"));
					aProduct.setLong_description(result.getString("long_description"));
					aProduct.setCategory(result.getString("category"));
					aProduct.setTag(result.getString("tag"));
					aProduct.setTag(result.getString("tag"));
					aProduct.setWeight(result.getString("weight"));
					aProduct.setSize(result.getString("size"));
					aProduct.setPrice(result.getInt("price"));
					aProduct.setImage(result.getString("image"));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return aProduct;
		}
		
		
		
		public List<Product> selectAllProductByName(String search){
			List<Product> Products = new ArrayList<>();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_Product_BY_NAME);
				preparedStatement.setString(1,'%' +search+ '%');
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					int id = resultSet.getInt("id");
					String name = resultSet.getString("name");
					String description = resultSet.getString("description");
					String category = resultSet.getString("category");
					String tag = resultSet.getString("tag");
					String long_description = resultSet.getString("long_description");
					String weight = resultSet.getString("weight");
					String size = resultSet.getString("size");
					String image = resultSet.getString("image");
					int price = resultSet.getInt("price");
					Product b = new Product(name,description,category,tag,long_description,weight,size,image,id,price);
					Products.add(b);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return Products;
		}
		
	
}