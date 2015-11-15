package intszol;

public class comment {
	protected int image_id;
	protected int user_id;
	protected String content;
	protected String date;
	
  public String toString() {
    return image_id + "\t" + "\t" + user_id + "\t" + date + "\t" + content;
  }
}
