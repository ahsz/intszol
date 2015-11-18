package intszol;

 /* Use of the class
*|*
*|* Have to be added the following external archive build paths:
*|*	- mysql-connector-java-[version]-bin.jar
*|*	- org.eclipse.jdt.annotation_[version].jar
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public class Utility {
	
	private Connection conn = null;
	
	
	 /* Constructor
	*|* 
	*|* Set up DB connection.
	*/
	public Utility(){
		try {
            String userName = "dbuser";
            String password = "giantmouse159";

            String url = "jdbc:mysql://25.63.189.116:3306/intszol";
            try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
            conn = DriverManager.getConnection(url, userName, password);
			
			} catch (SQLException ex) {				
			   System.out.println("SQLException: " + ex.getMessage());
			   System.out.println("SQLState: " + ex.getSQLState());
			   System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	}

	 /* Get user
	*|* 
	*|* Expect the parameters of the search:
	*|*		- user_id
	*|*
	*|* Return with a user object, contain the all metadata of user. 
	*/ 
	public User get_user(int id){
	
		User usr = new User();
		
		try {	
			// Search for the comment(s)
			String query = "SELECT * FROM user WHERE id = ?";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, id); 	
	
			
			// Fill up the object with the user's metadatas
			ResultSet r = stmt.executeQuery();
				
			r.next();
			usr.id = r.getInt("id");
			usr.name = r.getString("name");
			
		} catch (SQLException ex) {
		   System.out.println("SQLException: " + ex.getMessage());
		   System.out.println("SQLState: " + ex.getSQLState());
		   System.out.println("VendorError: " + ex.getErrorCode());
		}
		return usr;
	}
	
	 /* Add new image
	*|* 
	*|* Expect the parameters of the image:
	*|*		- user_id
	*|*		- image name
	*|*		- date (nullable)
	*|*		- place (nullable)
	*|*
	*|* If date is null, the date will be the sysdate of the MySQL server.
	*|*
	*|* Return with the ID of the newly added image.
	*/
	public int add_image(int user_id, String name, @Nullable String date, @Nullable String place){
		
		int image_id = 0;
		
		try {
			
			// Add the new image
			String query = "INSERT INTO image VALUES (null,?,?,";
			
			if (date != null)									
				query += "?,";
			else
				query += "sysdate(),";
			if (place != null)
				query += "?)";
			else
				query += "null)";		
			
			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			int i=1;
			stmt.setInt(i, user_id);
			i++;
			stmt.setString(i, name);
			i++;
			if (date != null)	{ stmt.setString(i, date); 	i++; }
			if (place != null)	{ stmt.setString(i, place); 	 }
			
			stmt.execute();
			
			// Query the ID of the newly added image.
			query = "SELECT * FROM image WHERE user_id = ? AND name = ?";			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, user_id);
			stmt.setString(2, name);
			ResultSet r = stmt.executeQuery();
			r.next();
			image_id = r.getInt("id");

		} catch (SQLException ex) {
			   System.out.println("SQLException: " + ex.getMessage());
			   System.out.println("SQLState: " + ex.getSQLState());
			   System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return image_id;
	}

	 /* Get image
	*|* 
	*|* Expect the parameters of the search:
	*		- image_id
	*|*		- user_id
	*|*		- image name
	*|*		- date_from
	*|*		- date_to
	*|*		- place
	*|*
	*|* All parameter can be null!
	*|*
	*|* If date_from is not null but date_to is null
	*|*		--> date_to = date_from
	*|* If date_to is not null but date_from is null
	*|* 		--> date_from = date_to	
	*|*
	*|* Return with a List<image> list, contain the all metadata of images. 
	*/ 
	public List<Image> get_image(java.lang.Integer id, java.lang.Integer user_id, @Nullable String name, @Nullable String date_from, @Nullable String date_to, @Nullable String place){

		List<Image> img_list = new ArrayList<Image>();
		
		if (date_from == null && date_to != null) 
			date_from = date_to;
		if (date_to == null && date_from != null)
			date_to = date_from;
			
		try {	
			// Search for the image(s)
			String query = "SELECT * FROM image";
			if (id != null || user_id != null || name != null || date_from != null || date_to != null || place != null )	
				query += " WHERE";
			if (id != null)									
				query += " id = ?";
			if (id != null && user_id != null)
				query += " AND";
			if (user_id != null)									
				query += " user_id = ?";
			if ((id != null || user_id != null) && name != null)					
				query += " AND";
			if (name != null)										
				query += " name = ?";
			if ((id != null || user_id != null || name != null) && date_from != null)
				query += " AND";			
			if (date_from != null)
				query += " date >= ? AND date <= ?";	
			if ((id != null || user_id != null || name != null || date_from != null) && place != null)
				query += " AND";
			if (place != null)
				query += " place = ?";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			int i=1;
			if (id != null)	{ stmt.setInt(i, id); 	i++; } 
			if (user_id != null)	{ stmt.setInt(i, user_id); 	i++; } 
			if (name != null) 		{ stmt.setString(i, name); 	i++; }
			if (date_from != null) 	{ stmt.setString(i, date_from); i++; }
			if (date_to != null) 	{ stmt.setString(i, date_to); i++; }
			if (place != null)		{ stmt.setString(i, place); i++; }
			
			// Fill up the list with the image(s)'s metadata(s)
			ResultSet r = stmt.executeQuery();
			Image img = null;
			while (r.next()){
				img = new Image();
				img.id = r.getInt("id");
				img.user_id = r.getInt("user_id");
				img.name = r.getString("name");
				img.date = r.getString("date");
				img.place = r.getString("place");		
				img_list.add(img);
			}
			
		    // Uncomment for testing
			/*
			ResultSet r = stmt.executeQuery();
			
			ResultSetMetaData rsmd = r.getMetaData();
			for (int j = 1; j <= rsmd.getColumnCount(); j++) {
				System.out.print(rsmd.getColumnName(j));
				System.out.print( "\t\t");
				}
			System.out.println();
			while (r.next()) {
				int i1 = r.getInt("id");
				int i2 = r.getInt("user_id");
				String s1 = r.getString("name");
				String s2 = r.getString("date");
				String s3 = r.getString("place");
				System.out.print(i1 + "\t\t" + i2 + "\t\t" + s1 + "\t\t" + s2 + "\t" + s3 + "\n");
			}
			*/	
			
		} catch (SQLException ex) {
		   System.out.println("SQLException: " + ex.getMessage());
		   System.out.println("SQLState: " + ex.getSQLState());
		   System.out.println("VendorError: " + ex.getErrorCode());
		}
		return img_list;
	}

	 /* Delete image
	*|* 
	*|* !!! IF USER_ID AND IMAGE BOTH NULL, ALL IMAGE WILL BE DELETED !!!
	*|*
	*|* Expect the parameters of the image:
	*|*		- user_id
	*|*		- image name (nullable)
	*|*
	*|* If image is null, all image of the user will be deleted.
	*|* 
	*|* !!! IF USER_ID AND IMAGE BOTH NULL, ALL IMAGE WILL BE DELETED !!!
	*/
	public void delete_image(java.lang.Integer user_id, @Nullable String name){
		try {	
			// Delete the image
			String query = "DELETE FROM image ";
			if (user_id != null){
				query += "WHERE user_id = ?";
				if (name != null)
					query += " AND name = ?";
			}

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			int i=1;
			if (user_id != null) 	{stmt.setInt(i, user_id); 	i++; }
			if (name != null) 		{ stmt.setString(i, name); 	i++; }
		
			stmt.execute();
			
		} catch (SQLException ex) {
		   System.out.println("SQLException: " + ex.getMessage());
		   System.out.println("SQLState: " + ex.getSQLState());
		   System.out.println("VendorError: " + ex.getErrorCode());
		}
	}		
		
	 /* Add new comment
	 |* 
	 |* Expect the parameters of the comment:
  	 |* 	- image_id
  	 |* 	- user_id
	 |* 	- content
	 |* 
	 |* Date will be the sysdate of the MySQL server.
	*/
	public void add_comment(int image_id, int user_id, String content){
		try {
			
			// Add the new comment
			String query = "INSERT INTO comment VALUES (null,?,?,?,sysdate())";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, image_id);
			stmt.setInt(2, user_id);
			stmt.setString(3, content);
			
			stmt.execute();
			
		} catch (SQLException ex) {
			   System.out.println("SQLException: " + ex.getMessage());
			   System.out.println("SQLState: " + ex.getSQLState());
			   System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	 /* Get comment
	 |* 
	 |* Expect the parameters of the comment:
	 |* 	- image_id (nullable)
	 |* 	- user_id (nullable)
	 |*
	 |* Return with a List<comment> list, contain the all metadata of comments.
	*/
	public List<Comment> get_comment(java.lang.Integer image_id, java.lang.Integer user_id){

		List<Comment> cmt_list = new ArrayList<Comment>();
		
		try {	
			// Search for the comment(s)
			String query = "SELECT * FROM comment";
			if (image_id != null && user_id != null)
				query += " WHERE image_id = ? AND user_id = ?";
			if (image_id != null && user_id == null)
				query += " WHERE image_id = ?";
			if (image_id == null && user_id != null)
				query += " WHERE user_id = ?";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			if (image_id != null && user_id != null){
				stmt.setInt(1, image_id); 	
				stmt.setInt(2, user_id); 
			}
			if (image_id != null && user_id == null)
				stmt.setInt(1, image_id); 
			if (image_id == null && user_id != null)
				stmt.setInt(1, user_id);
			
			// Fill up the list with the comment(s)'s metadata(s)
			ResultSet r = stmt.executeQuery();
				
			Comment cmt = null;
			while (r.next()){
				cmt = new Comment();
				cmt.image_id = r.getInt("image_id");
				cmt.user_id = r.getInt("user_id");
				cmt.date = r.getString("date");
				cmt.content = r.getString("content");

				cmt_list.add(cmt);
			}
		
		} catch (SQLException ex) {
		   System.out.println("SQLException: " + ex.getMessage());
		   System.out.println("SQLState: " + ex.getSQLState());
		   System.out.println("VendorError: " + ex.getErrorCode());
		}
		return cmt_list;
	}
	
	 /* Delete comment
	 |* 
	 |* !!! IF IMAGE_ID AND USER_ID BOTH NULL, ALL COMMENT WILL BE DELETED !!!
	 |*
	 |* Expect the parameters of the comment:
	 |* 	- image_id
	 |* 	- user_id
	 |*
	 |* If image_id is null, all comment of the user will be deleted.
	 |* If user_id is null, all comment of the image will be deleted.
	 |*
	 |* !!! IF IMAGE_ID AND USER_ID BOTH NULL, ALL COMMENT WILL BE DELETED !!!
	*/
	public void delete_comment(java.lang.Integer image_id, java.lang.Integer user_id){
		try {
			// Delete the comment
			String query = "DELETE FROM comment ";
			if (image_id != null && user_id != null)
				query += "WHERE image_id = ? AND user_id = ?";
			if (image_id != null && user_id == null)
				query += "WHERE image_id = ?";
			if (image_id == null && user_id != null)
				query += "WHERE user_id = ?";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			if (image_id != null && user_id != null){
				stmt.setInt(1, image_id); 	
				stmt.setInt(2, user_id); 
			}
			if (image_id != null && user_id == null)
				stmt.setInt(1, image_id); 
			if (image_id == null && user_id != null)
				stmt.setInt(1, user_id);

			stmt.execute();
			
		} catch (SQLException ex) {
		   System.out.println("SQLException: " + ex.getMessage());
		   System.out.println("SQLState: " + ex.getSQLState());
		   System.out.println("VendorError: " + ex.getErrorCode());
		}
	}		

	 /* Add new annotation
	 |* 
	 |* Expect the parameters of the comment:
	 |*		- image_id
	 |* 	- content
	*/
	public void add_annotation(int image_id, String content){
		try {
			
			// Add the new comment
			String query = "INSERT INTO annotation VALUES (null,?,?)";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, image_id);
			stmt.setString(2, content);
			
			stmt.execute();
			
		} catch (SQLException ex) {
			   System.out.println("SQLException: " + ex.getMessage());
			   System.out.println("SQLState: " + ex.getSQLState());
			   System.out.println("VendorError: " + ex.getErrorCode());
		}	
	}
	
	 /* Get annotation
	 |* 
	 |* Expect the parameters of the comment:
	 |* 	- image_id (nullable)
	 |* 	
	 |* Return with a List<annotation> list, contain the all metadata of annotation(s).
	*/
	public List<Annotation> get_annotation(java.lang.Integer image_id){

		List<Annotation> ano_list = new ArrayList<Annotation>();
		
		try {	
			// Search for the comment(s)
			String query = "SELECT * FROM annotation";
			if (image_id != null)
				query += " WHERE image_id = ?";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			if (image_id != null)
				stmt.setInt(1, image_id); 	

			
			// Fill up the list with the annotation(s)'s metadata(s)
			ResultSet r = stmt.executeQuery();
				
			Annotation ano = null;
			while (r.next()){
				ano = new Annotation();
				ano.image_id = r.getInt("image_id");
				ano.content = r.getString("content");


				ano_list.add(ano);
			}
		
		} catch (SQLException ex) {
		   System.out.println("SQLException: " + ex.getMessage());
		   System.out.println("SQLState: " + ex.getSQLState());
		   System.out.println("VendorError: " + ex.getErrorCode());
		}
		return ano_list;
	}
	
	
	
	 /* Delete annotation
	 |* 
	 |* !!! IF IMAGE_ID AND CONTENT BOTH NULL, ALL COMMENT WILL BE DELETED !!!
	 |*
	 |* Expect the parameters of the comment:
	 |* 	- image_id
	 |* 	- content
	 |*
	 |* If content is null, all annotation of the image will be deleted.
	 |*
	 |* !!! IF IMAGE_ID AND CONTENT BOTH NULL, ALL COMMENT WILL BE DELETED !!!
	*/
	public void delete_annotation(java.lang.Integer image_id, @Nullable String content){
		try {
			// Delete the annotation(s)
			String query = "DELETE FROM annotation ";
			if (image_id != null && content != null)
				query += "WHERE image_id = ? AND content = ?";
			if (image_id != null && content == null)
				query += "WHERE image_id = ?";
			if (image_id == null && content != null)
				query += "WHERE content = ?";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			if (image_id != null && content != null){
				stmt.setInt(1, image_id); 	
				stmt.setString(2, content); 
			}
			if (image_id != null && content == null)
				stmt.setInt(1, image_id); 
			if (image_id == null && content != null)
				stmt.setString(1, content);	

			stmt.execute();
			
		} catch (SQLException ex) {
		   System.out.println("SQLException: " + ex.getMessage());
		   System.out.println("SQLState: " + ex.getSQLState());
		   System.out.println("VendorError: " + ex.getErrorCode());
		}
	}	
}


