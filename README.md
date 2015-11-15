# intszol
intszol repo


package intszol;

-----------------------
---------IMAGE---------
-----------------------

Structure class for image representation.

--Members--
- id
- user_id
- name
- date
- place

--Methods--
- toString

-----------------------
--------UTILITY--------
-----------------------
Use of the class

Have to be added the following external archive build paths:
- mysql-connector-java-[version]-bin.jar
- org.eclipse.jdt.annotation_[version].jar

--Constructor--

Set up DB connection.


--Search image--

Expect the parameters of the search:
- user_id
- image name
- date_from
- date_to
- place

All parameter can be null!

If date_from is not null but date_to is null
--> date_to = date_from
date_to is not null but date_from is null
--> date_from = date_to	

Return with a List<image> list, contain the all metadata of images. 


--New image--

Expect the parameters of the image:
- user_id
- image name
- date (nullable)
- place (nullable)

If date is null, the date will be the sysdate of the MySQL server.

Return with the ID of the newly added image.


--Delete image--

!!! IF USER_ID AND IMAGE BOTH NULL, ALL IMAGE IS DELETED !!!

Expect the parameters of the image:
- user_id
- image name (nullable)

If image is null, all image of the user will be deleted.

!!! IF USER_ID AND IMAGE BOTH NULL, ALL IMAGE IS DELETED !!!



