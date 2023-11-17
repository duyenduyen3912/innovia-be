package com.datn.product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.datn.model.Product;
import com.datn.model.Review;
import com.datn.response.ListProduct;
import com.datn.searchByImage.Image;
import com.datn.searchByImage.ImageProcess;
import com.datn.searchByImage.ImageResult;


import weka.core.Instances;




public class ProductDAO {
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
		private String jdbcURL = "jdbc:mysql://localhost:3306/datn";
		private String jdbcUsername = "root";
		private String jdbcPassword = "tothichmeou39";
		
		private static final String SELECT_ALL_Product = "select * from product ORDER BY star DESC";
		private static final String SELECT_ALL_Product_CATEGORY = "select * from product where category=?";
		private static final String SELECT_Product_BY_ID ="select * from product where id= ?";
		private static final String SELECT_CATEGORY = "select distinct category from product";
		private static final String SEARCH_Product_BY_NAME ="select * from product where name like ?";
		private static final String SEARCH_Product_BY_IMAGE ="select * from product where image like ?";
		private static final String ADD_REVIEW = "insert into rate (idproduct, star, comment) values (?,?,?);";
		private static final String SELECT_REVIEW = "SELECT * FROM rate WHERE idproduct = ?";
		private static final String UPDATE_STAR = "UPDATE product\r\n"
												+ "SET star = ROUND(\r\n"
												+ "    (SELECT AVG(star) FROM rate WHERE rate.idproduct = ?),\r\n"
												+ "    1\r\n"
												+ ")\r\n"
												+ "WHERE product.id = ?;";
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
					float star = resultSet.getFloat("star");
					Product b = new Product(name,description, category, tag, long_description, weight, size, image, idProduct, price, star);
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
					aProduct.setStar(result.getFloat("star"));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return aProduct;
		}
		
		public List<String> selectCategory() {
			List<String> category = new ArrayList<>();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY);
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					String c = resultSet.getString("category");
					category.add(c);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return category;
		}
		
		public List<Product> selectAllProductByCategory (String label) {
	
			List<Product> Products = new ArrayList<>();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_Product_CATEGORY);
				preparedStatement.setString(1,label);
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
					float star = resultSet.getFloat("star");
					Product b = new Product(name,description,category,tag,long_description,weight,size,image,id,price, star);
					Products.add(b);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return Products;
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
					float star = resultSet.getFloat("star");
					Product b = new Product(name,description,category,tag,long_description,weight,size,image,id,price, star);
					Products.add(b);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return Products;
		}
		
		public ListProduct selectAllProductByImage(String search){
			ImageProcess imageProcess = new ImageProcess();
			ImageResult res = new ImageResult();
			Image resInstance = new Image();
			String imageLabel = null;
			List<String> image_result = new ArrayList();
			List<Product> products = new ArrayList<>();
			List<Product> recommend = new ArrayList<>();
			ListProduct result_list = null;
			try {
				String data = imageProcess.loadImageFromMemory(search);
				Instances dataset = imageProcess.createInstance(data);
				
				Instances predict_label = imageProcess.createInstanceLabel(data);
				
				Instances extract = imageProcess.extractColorHistogram(dataset);
				
				Instances imageNeighbors = resInstance.SearchImage(extract);
				
				imageLabel = resInstance.predictLabel(predict_label);
				
				if(imageNeighbors.size() != 0) {
					image_result = res.Result(imageNeighbors);
					products = getProductByImage(image_result);
					recommend = selectAllProductByCategory(imageLabel);
				}
				else return result_list = new ListProduct(Collections.emptyList(), Collections.emptyList());
				imageProcess.deleteImageFile(data);
			} catch (IOException e) {
	            e.printStackTrace();
	        }
			
			return result_list = new ListProduct(products, recommend);
		}
		
		private List<Product> getProductByImage (List<String> image_result ){
			List<Product> Products = new ArrayList<>();
			
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_Product_BY_IMAGE);
				Set<Integer> addedIds = new HashSet<>();
				for (String imageIndex : image_result) {
				
					preparedStatement.setString(1,'%' +imageIndex+ '%');
					ResultSet resultSet = preparedStatement.executeQuery();
					if (resultSet.next())
					{
						int id = resultSet.getInt("id");
						if(!addedIds.contains(id)) {
							String name = resultSet.getString("name");
							String description = resultSet.getString("description");
							String category = resultSet.getString("category");
							String tag = resultSet.getString("tag");
							String long_description = resultSet.getString("long_description");
							String weight = resultSet.getString("weight");
							String size = resultSet.getString("size");
							String image = resultSet.getString("image");
							int price = resultSet.getInt("price");
							float star = resultSet.getFloat("star");
							Product b = new Product(name,description,category,tag,long_description,weight,size,image,id,price, star);
							Products.add(b);
							addedIds.add(id);
						} 
						
					} 
					} 
				
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				return Products;
			}
		
		public String insertReview(Review r) {
			try(Connection connection = getConnection()) {
				
				PreparedStatement ps = connection.prepareStatement(ADD_REVIEW);
				int result = 0;
				ps.setInt(1, r.getIdproduct());
				ps.setInt(2, r.getStar());
				ps.setString(3, r.getComment());
				result = ps.executeUpdate();
				UpdateStarProduct(r.getIdproduct());
			} catch(Exception e) {
				e.printStackTrace();
			}
			return "success";
		}
		
		public List<Review> getReviewOfProduct(int idproduct) {
			List<Review> r = new ArrayList();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REVIEW);
				preparedStatement.setInt(1,idproduct);
				ResultSet result = preparedStatement.executeQuery();
				while(result.next())
				{
					int id = result.getInt("idproduct");
					int star = result.getInt("star");
					String comment = result.getString("comment");
					
					Review list_review = new Review(idproduct, star, comment);
					r.add(list_review);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return r;
		}
		
		private void UpdateStarProduct (int idproduct)  {
			try (Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STAR);
				preparedStatement.setInt(1, idproduct);
				preparedStatement.setInt(2, idproduct);
				preparedStatement.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
}