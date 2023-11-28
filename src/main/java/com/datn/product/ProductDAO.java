package com.datn.product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.datn.model.BestSelling;
import com.datn.model.Cart;
import com.datn.model.Id;
import com.datn.model.PCart;
import com.datn.model.Product;
import com.datn.model.Review;
import com.datn.model.Order;
import com.datn.model.OrderList;
import com.datn.model.OrderItem;
import com.datn.response.ListProduct;
import com.datn.response.OrderResponse;
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
		private static final String ADD_PRODUCT_TO_CART = "INSERT INTO cart (iduser, idproduct, quantity, note)\r\n"
															+ "VALUES (?, ?, ?, ?)\r\n"
															+ "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity), note = VALUES(note);";
		private static final String SELECT_PRODUCT_IN_CART = "SELECT cart.idcart, cart.iduser, cart.idproduct, cart.quantity, cart.note, product.name, product.price, product.image\r\n"
															+ "FROM cart\r\n"
															+ "JOIN product ON cart.idproduct = product.id\r\n"
															+ "WHERE cart.iduser = ?;\r\n";
		private static final String UPDATE_CART = "UPDATE cart SET quantity = ? WHERE iduser = ? AND idproduct = ?";
		private static final String DELETE_CART = "DELETE FROM cart WHERE iduser = ? AND idproduct = ?";
		private static final String ORDER = "INSERT INTO orders (id, iduser, listidproduct, createtime, status, address, phone, totalmoney) values (?,?,?,?,?,?,?,?)";
		private static final String UPDATE_PRODUCT = "UPDATE product_inventory SET sold = sold + ?, inventory = total - sold WHERE idproduct = ?";
		private static final String SELECT_ORDER = "select * from orders where iduser = ?";
		private static final String UPDATE_STAR = "UPDATE product\r\n"
												+ "SET star = ROUND(\r\n"
												+ "    (SELECT AVG(star) FROM rate WHERE rate.idproduct = ?),\r\n"
												+ "    1\r\n"
												+ ")\r\n"
												+ "WHERE product.id = ?;";
		public static final String SELECT_BEST_SELLING_PRODUCT = "SELECT product.id, product.name , product.price , product.image \r\n"
									+ "FROM product \r\n"
									+ "JOIN product_inventory ON product.id = product_inventory.idproduct\r\n"
									+ "ORDER BY product_inventory.sold DESC LIMIT 5";
		private static final String SELECT_INFOR_ORDER = "SELECT listidproduct, totalmoney FROM orders WHERE id = ?";
		private static final String SELECT_ORDER_ITEM = "select product.name, product.image, product.price from product where product.id = ?";
		private static final String CANCEL_ORDER = "DELETE FROM orders WHERE iduser = ? AND id = ?";		
		
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
				Instances extract_label = imageProcess.extractColorHistogram(predict_label);
				
				Instances imageNeighbors = resInstance.SearchImage(extract);
				
				imageLabel = resInstance.predictLabel(extract_label);
				
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
		
		public String AddProductToCart(Cart c) {
			try (Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_TO_CART);
				int result = 0;
				preparedStatement.setInt(1, c.getIduser());
				preparedStatement.setInt(2, c.getIdproduct());
				preparedStatement.setInt(3, c.getQuantity());
				preparedStatement.setString(4, c.getNote());
				int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return "OK";
                } 
			} catch(Exception e) {
				e.printStackTrace();
			}
			return "error";
		}
		
		public List<PCart> selectProductInCart(int idUser) {
			List<PCart> listProduct = new ArrayList<>();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_IN_CART);
				preparedStatement.setInt(1,idUser);
				ResultSet result = preparedStatement.executeQuery();
				while(result.next())
				{
					int iduser = result.getInt("iduser");
					int idproduct =result.getInt("idproduct");
					int quantity =result.getInt("quantity");
					int price =result.getInt("price");
					int total =result.getInt("quantity")*result.getInt("price");
					String image = result.getString("image");
					String name = result.getString("name");
					PCart c = new PCart(iduser, idproduct,quantity, price,total, image, name);
					listProduct.add(c);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return listProduct;
		}
		
		public String UpdateCart (Cart c)  {
			try (Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CART);
				preparedStatement.setInt(1, c.getQuantity());
				preparedStatement.setInt(2, c.getIduser());
				preparedStatement.setInt(3, c.getIdproduct());
				int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return "OK";
                } 
			}catch(Exception e) {
				e.printStackTrace();
			}
			return "error";
		}
		
		public String DeleteCart (Cart c)  {
			try (Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CART);
				preparedStatement.setInt(1, c.getIduser());
				preparedStatement.setInt(2, c.getIdproduct());
				int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return "OK";
                } 
			}catch(Exception e) {
				e.printStackTrace();
			}
			return "error";
		}
		
		
		public String Order(Order o) {
			try(Connection connection = getConnection()) {
				PreparedStatement ps = connection.prepareStatement(ORDER);
				int result = 0;
				LocalDateTime currentDateTime = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				DateTimeFormatter formatterId = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
				String formattedDateTime = currentDateTime.format(formatter);
				String formattedDateTimeId = currentDateTime.format(formatterId);
				ps.setString(1, formattedDateTimeId+"_"+String.valueOf(o.getIduser()));
				ps.setInt(2,  o.getIduser());
				ps.setString(3, o.getIdproduct());
				ps.setString(4, formattedDateTime);
				ps.setString(5,  "Đang chuẩn bị");
				ps.setString(6, o.getAddress());
				ps.setString(7, o.getPhone());
				ps.setInt(8, o.getTotalmoney());
				result = ps.executeUpdate();
				UpdateQuantity(o.getIdproduct());
				if (result > 0) {
                    return "OK";
                } 
			}catch(Exception e) {
				e.printStackTrace();
			}
			return "error";
		}
		
		private void UpdateQuantity (String listProduct) {
			try(Connection connection = getConnection()) {
				 String[] pairs = listProduct.split(";");
		            for (String pair : pairs) {
		                String[] values = pair.split("-");
		                int idProduct = Integer.parseInt(values[0]);
		                int quantity = Integer.parseInt(values[1]);
		                
		                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT);
						preparedStatement.setInt(1, quantity);
						preparedStatement.setInt(2, idProduct);
						preparedStatement.executeUpdate();
		            }
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public List<OrderList> selectOrders (int iduser) {
			List<OrderList> orderList = new ArrayList<>();
			try(Connection connection = getConnection()) {
		                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER);
		                preparedStatement.setInt(1, iduser);
		                ResultSet result = preparedStatement.executeQuery();
		                while(result.next()) {
		                	String id = result.getString("id");
		                	String date = result.getString("createtime");
		                	String status = result.getString("status");
		                	int total = result.getInt("totalmoney");
		                	OrderList o = new OrderList(id, date, status, total);
		                	orderList.add(o);
		            }
			} catch(Exception e) {
				e.printStackTrace();
			}
			return orderList;
		}
		
		public List<BestSelling> selectBestSellingProduct () {
			List<BestSelling> bestList = new ArrayList<>();
			try(Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BEST_SELLING_PRODUCT);
				ResultSet result = preparedStatement.executeQuery();
                while(result.next()) {
                	int id = result.getInt("id");
                	String name = result.getString("name");
                	String image = result.getString("image");
                	int price = result.getInt("price");
                	BestSelling o = new BestSelling(name, image, price, id);
                	bestList.add(o);
            }
			}catch(Exception e) {
				e.printStackTrace();
			}
			return bestList;
		}
		
		public OrderResponse selectOrder (String id) {
			OrderResponse orderResponse = null;
			List<OrderItem> o = new ArrayList<>();
			try(Connection connection = getConnection()) {
		                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INFOR_ORDER);
		                preparedStatement.setString(1, id);
		                ResultSet result = preparedStatement.executeQuery();
		                if(result.next()) {
		                	String listProduct = result.getString("listidproduct");
		                	int total = result.getInt("totalmoney");
		                	String[] pairs = listProduct.split(";");
				            for (String pair : pairs) {
				                String[] values = pair.split("-");
				                int idProduct = Integer.parseInt(values[0]);
				                int quantity = Integer.parseInt(values[1]);
				                
				                PreparedStatement ps = connection.prepareStatement(SELECT_ORDER_ITEM);
				                ps.setInt(1, idProduct);
				                ResultSet result2 = ps.executeQuery();
				                if(result2.next()) {
				                	String name = result2.getString("name");
				                	String image = result2.getString("image");
				                	int price = result2.getInt("price");
				                	OrderItem orderItem = new OrderItem(name, image, price, quantity);
				                	o.add(orderItem);
				                }
				            }
				            return new OrderResponse(id,total,o);
		                }
		            
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		
		}
		
		public String CancelOrder (Id i)  {
			try (Connection connection = getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement(CANCEL_ORDER);
				preparedStatement.setInt(1, Integer.parseInt(i.getAuth()));
				preparedStatement.setString(2, i.getId());
				int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    return "OK";
                } 
			}catch(Exception e) {
				e.printStackTrace();
			}
			return "error";
		}
		
		
}