# intszol
intszol repo


package intszol;

Have to be added the following external archive build paths to the package:
- mysql-connector-java-[version]-bin.jar
- org.eclipse.jdt.annotation_[version].jar

-----------------------
--------USER--------
-----------------------
Structure class for users.

Members:
- protected int id
- protected int name

-----------------------
---------IMAGE---------
-----------------------
Structure class for images.

Members:
- protected int id
- protected int user_id
- protected String name
- protected String date
- protected String place

-----------------------
--------COMMENT--------
-----------------------
Structure class for comments.

Members:
- protected int image_id
- protected int iuser_id
- protected String content
- protected String date
	
-----------------------
--------UTILITY--------
-----------------------
Class for all database transaction.

Members:
- private Connection conn

Methods:

- Constructor

  Set up DB connection.



- get_user

  Get user's metadata from the database.
  
  Parameters:
	- user_id

  Return:
  
	- user (object)
	
	


- add_image

  Add image's metadata to the database.

  Parameters:
	- user_id
	- image name
	- date (nullable)
	- place (nullable)
	
	The parameters of the image.

	If date is null, the date will be the sysdate of the MySQL server.

  Return:
  
	- integer
  
	The ID of the newly created image.




- get_images

  Get images' metadatas from the database.

  Parameters:
	- user_id (nullable)
	- image name (nullable)
	- date_from (nullable)
	- date_to (nullable)
	- place (nullable)

	The parameters of the search.
	
	All parameter can be null!
	
	If date_from is not null but date_to is null --> date_to = date_from
	
	If date_to is not null but date_from is null --> date_from = date_to	

  Return:

	- List<image> 

	contain the all metadata of images. 




- delete_image

  !!! IF USER_ID AND IMAGE BOTH NULL, ALL IMAGE IS DELETED !!!
  
  Delete images' metadate from the database.

  Parameters:
	- user_id
	- image name (nullable)
	
	The parameters of the image.

	If image is null, all image of the user will be deleted.

  Return:
	- void

  !!! IF USER_ID AND IMAGE BOTH NULL, ALL IMAGE IS DELETED !!!
 
 
  
  
- add_comment

  Add comment's metadata to the database.

  Parameters:
	- image_id
	- user_id
	- content
  
	The parameters of the comment.

	Date will be the sysdate of the MySQL server.

  Return:
	- void




- get_comment

  Get comments' metadatas from the database.

  Parameters:
	- image_id (nullable)
	- user_id (nullable)

	The parameters of the comment.

  Return:
	- List<comment>
	
	Contain the all metadata of comments.




- delete_comment
 
  !!! IF IMAGE_ID AND USER_ID BOTH NULL, ALL COMMENT WILL BE DELETED !!!
  
  Delete comments' metadatas from the database.

  Parameters:
	- image_id
	- user_id
  
	Parameters of the comment.

  	If image_id is null, all comment of the user will be deleted.

  	If user_id is null, all comment of the image will be deleted.


  Return:
	- void

  !!! IF IMAGE_ID AND USER_ID BOTH NULL, ALL COMMENT WILL BE DELETED !!!




- add_annotation

  Add annotation's metadata to the database.

  Parameters:
	- image_id
	- content
  
	The parameters of the annotation.

  Return:
	- void




- get_annotation

  Get annotations' metadatas from the database.

  Parameters:
	- image_id (nullable)

	The parameters of the annotation.

  Return:
	- List<annotation>
	
	Contain the all metadata of annotations.




- delete_annotation
 
  !!! IF IMAGE_ID AND CONTENT BOTH NULL, ALL COMMENT WILL BE DELETED !!!
  
  Delete comments' metadatas from the database.

  Parameters:
	- image_id
	- content
  
	Parameters of the annotation.

  	If content is null, all annotation of the image will be deleted.

  Return:
	- void

  !!! IF IMAGE_ID AND CONTENT BOTH NULL, ALL COMMENT WILL BE DELETED !!!
