package intszol;

 /* Use of the class
*|*
*|* Have to be added the following external archive build paths:
*|*	- mysql-connector-java-[version]-bin.jar
*|*	- org.eclipse.jdt.annotation_[version].jar														*|
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

public class utility {
	
	private Connection conn = null;
	
	
	 /* Constructor
	*|* 
	*|* Set up DB connection.
	*/
	public utility(){
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

	
	 /*Search image
	*|* 
	*|* Expect the parameters of the search:
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
	public List<image> search_image(java.lang.Integer user_id, @Nullable String name, @Nullable String date_from, @Nullable String date_to, @Nullable String place){

		List<image> img_list = new ArrayList<image>();
		
		if (date_from == null && date_to != null) 
			date_from = date_to;
		if (date_to == null && date_from != null)
			date_to = date_from;
			
		try {	
			// Search for the image(s)
			String query = "SELECT * FROM image";
			if (user_id != null || name != null || date_from != null || date_to != null || place != null )	
				query += " WHERE";
			if (user_id != null)									
				query += " user_id = ?";
			if (user_id != null && name != null)					
				query += " AND";
			if (name != null)										
				query += " name = ?";
			if ((user_id != null || name != null) && date_from != null)
				query += " AND";			
			if (date_from != null)
				query += " date >= ? AND date <= ?";	
			if ((user_id != null || name != null || date_from != null) && place != null)
				query += " AND";
			if (place != null)
				query += " place = ?";

			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			
			int i=1;
			if (user_id != null)	{ stmt.setInt(i, user_id); 	i++; } 
			if (name != null) 		{ stmt.setString(i, name); 	i++; }
			if (date_from != null) 	{ stmt.setString(i, date_from); i++; }
			if (date_to != null) 	{ stmt.setString(i, date_to); i++; }
			if (place != null)		{ stmt.setString(i, place); i++; }
			
			// Fill up the list with the image(s)'s ID(s)
			ResultSet r = stmt.executeQuery();
			

			
			image img = null;
			while (r.next()){
				img = new image();
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

	
	 /* New image
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
	public int new_image(int user_id, String name, @Nullable String date, @Nullable String place){
		
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
			query = "SELECT * FROM image WHERE name = ?";			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
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

	 /* Delete image
	*|* 
	*|* !!! IF USER_ID AND IMAGE BOTH NULL, ALL IMAGE IS DELETED !!!
	*|*
	*|* Expect the parameters of the image:
	*|*		- user_id
	*|*		- image name (nullable)
	*|*
	*|* If image is null, all image of the user will be deleted.
	*|* 
	*|* !!! IF USER_ID AND IMAGE BOTH NULL, ALL IMAGE IS DELETED !!!
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
		
}


