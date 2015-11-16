package intszol;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		Utility ut = new Utility();
		int i;
		
		ut.delete_annotation(null, null);
		ut.delete_comment(null, null);
		ut.delete_image(null, null);
		
		System.out.print("Get user metadatas with user_id=1" + '\n');
		System.out.print("---------------------------------" + '\n');
		User usr = new User();
		usr = ut.get_user(1);
		System.out.print(usr + "\n" + "\n" + "\n");
		
		
		
		// New images
		System.out.print("Adding new images" + '\n');
		System.out.print("-----------------" + '\n');
		
		i = ut.add_image(1, "kep_1", "20150101", "Budapest");
		System.out.print("user 1, kep_1: " + i + '\n');
		i = ut.add_image(1, "kep_2", "20150101", null);
		System.out.print("user 1, kep_2: " + i + '\n');
		i = ut.add_image(1, "kep_3", null, "Budapest");
		System.out.print("user 1, kep_3: " + i + '\n');
		i = ut.add_image(1, "kep_4", null, null);
		System.out.print("user 1, kep_4: " + i + '\n');
		
		i = ut.add_image(2, "kep_1", "20150101", "Pécs");
		System.out.print("user 2, kep_1: " + i + '\n');
		i = ut.add_image(2, "kep_2", "20150101", null);
		System.out.print("user 2, kep_2: " + i + '\n');
		i = ut.add_image(2, "kep_3", null, "Pécs");
		System.out.print("user 2, kep_3: " + i + '\n');
		i = ut.add_image(2, "kep_4", null, null);
		System.out.print("user 2, kep_4: " + i + '\n' + '\n' + '\n');
		
		
		// Search user_id=2 user's images
		System.out.print("Get images with user_id=2" + '\n');
		System.out.print("-------------------------" + '\n');
		List<Image> img_list = new ArrayList<Image>();
		System.out.print("ID" + "\t" + "USER_ID" + "\t" + "NAME" + "\t" + "DATE" + "\t\t" + "PLACE" + "\n");
		img_list = ut.get_image(2, null, null, null, null);
		for (i=0; i<img_list.size(); i++){
			System.out.print(img_list.get(i) + "\n");
		}
	 
		System.out.print("\n" + "\n");
		
		// Add comment
		System.out.print("Add new comments" + '\n');
		System.out.print("----------------" + '\n');
		ut.add_comment(ut.get_image(1, "kep_1", null, null, null).get(0).id  , 1, "ez egy jó kép1");
		System.out.print("user_id = 1" + "\t" + "image_name = kep_1" + "\t" + "content = ez egy jó kép1" + "\n");
		ut.add_comment(ut.get_image(1, "kep_2", null, null, null).get(0).id  , 1, "ez egy jó kép2");
		System.out.print("user_id = 1" + "\t" + "image_name = kep_2" + "\t" + "content = ez egy jó kép2" + "\n");
		ut.add_comment(ut.get_image(1, "kep_2", null, null, null).get(0).id  , 1, "ez egy jó kép3");
		System.out.print("user_id = 1" + "\t" + "image_name = kep_2" + "\t" + "content = ez egy jó kép3" + "\n");
		ut.add_comment(ut.get_image(2, "kep_1", null, null, null).get(0).id  , 2, "ez egy jó kép4");
		System.out.print("user_id = 2" + "\t" + "image_name = kep_1" + "\t" + "content = ez egy jó kép4" + "\n");
		ut.add_comment(ut.get_image(2, "kep_2", null, null, null).get(0).id  , 2, "ez egy jó kép5");
		System.out.print("user_id = 2" + "\t" + "image_name = kep_2" + "\t" + "content = ez egy jó kép5" + "\n");
		ut.add_comment(ut.get_image(2, "kep_2", null, null, null).get(0).id  , 2, "ez egy jó kép6");
		System.out.print("user_id = 2" + "\t" + "image_name = kep_2" + "\t" + "content = ez egy jó kép6" + "\n");		
		
		System.out.print("\n" + "\n");
		
		// Search comment
		System.out.print("Get comments with user_id=1" + '\n');
		System.out.print("---------------------------" + '\n');
		List<Comment> cmt_list = new ArrayList<Comment>();
		System.out.print("IMAGE_ID" + "\t" + "USER_ID" + "\t" + "DATE" + "\t" + "\t" + "COMMENT" + "\n");
		cmt_list = ut.get_comment(null, 1);
		for (i=0; i<cmt_list.size(); i++){
			System.out.print(cmt_list.get(i) + "\n");
		}
		
		System.out.print("\n" + "\n");
		
		
		// Add annotation
		System.out.print("Add annotations" + '\n');
		System.out.print("---------------" + '\n');
		ut.add_annotation(ut.get_image(2, "kep_2", null, null, null).get(0).id, "#party");
		System.out.print("image_name = kep_2" + "\t" + "content = party" + "\n");
		ut.add_annotation(ut.get_image(2, "kep_2", null, null, null).get(0).id, "#balaton");
		System.out.print("image_name = kep_2" + "\t" + "content = balaton" + "\n");
		ut.add_annotation(ut.get_image(2, "kep_2", null, null, null).get(0).id, "#spenót");
		System.out.print("image_name = kep_2" + "\t" + "content = spenót" + "\n");
		ut.add_annotation(ut.get_image(1, "kep_3", null, null, null).get(0).id, "#party2");
		System.out.print("image_name = kep_3" + "\t" + "content = party2" + "\n");
		ut.add_annotation(ut.get_image(1, "kep_3", null, null, null).get(0).id, "#balaton2");
		System.out.print("image_name = kep_3" + "\t" + "content = balaton2" + "\n");
		ut.add_annotation(ut.get_image(1, "kep_3", null, null, null).get(0).id, "#spenót2");
		System.out.print("image_name = kep_3" + "\t" + "content = spenót2" + "\n");
		
		System.out.print("\n" + "\n");
		
		// Search comment
		System.out.print("Get annotations from kep_3" + '\n');
		System.out.print("--------------------------" + '\n');
		List<Annotation> ano_list = new ArrayList<Annotation>();
		System.out.print("IMAGE_ID" + "\t" + "CONTENT" + "\n");
		ano_list = ut.get_annotation(ut.get_image(1, "kep_3", null, null, null).get(0).id);
		for (i=0; i<ano_list.size(); i++){
			System.out.print(ano_list.get(i) + "\n");
		}
		
		System.out.print("\n" + "\n");
	}

}
