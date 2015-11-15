package intszol;

import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {

		utility ut = new utility();
		int i;
		
		ut.delete_image(null, null);
		
		// New images
		i = ut.new_image(1, "kep_1", "20150101", "Budapest");
		System.out.print("user 1, kep_1: " + i + '\n');
		i = ut.new_image(1, "kep_2", "20150101", null);
		System.out.print("user 1, kep_2: " + i + '\n');
		i = ut.new_image(1, "kep_3", null, "Budapest");
		System.out.print("user 1, kep_3: " + i + '\n');
		i = ut.new_image(1, "kep_4", null, null);
		System.out.print("user 1, kep_4: " + i + '\n');
		
		i = ut.new_image(2, "kep_1", "20150101", "Pécs");
		System.out.print("user 2, kep_1: " + i + '\n');
		i = ut.new_image(2, "kep_2", "20150101", null);
		System.out.print("user 2, kep_2: " + i + '\n');
		i = ut.new_image(2, "kep_3", null, "Pécs");
		System.out.print("user 2, kep_3: " + i + '\n');
		i = ut.new_image(2, "kep_4", null, null);
		System.out.print("user 2, kep_4: " + i + '\n' + '\n');
		
		
		// Search user_id=2 user's images
		List<image> img_list = new ArrayList<image>();
		System.out.print("ID" + "\t" + "USER_ID" + "\t" + "NAME" + "\t" + "DATE" + "\t\t" + "PLACE" + "\n");
		img_list = ut.search_image(2, null, null, null, null);
		for (i=0; i<img_list.size(); i++){
			System.out.print(img_list.get(i) + "\n");
		}
			 
	}

}
