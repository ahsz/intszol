package intszol;

class Image {
	public int id;
	public int user_id;
	public String name;
	public String date;
	public String place;
	public String gd_id;
	public String gd_url;
	public String gd_id2;
	public String gd_url2;
	
    public String toString() {
    	
        return id + "\t" + user_id + "\t" + name + "\t" + date + "\t" + place + "\t" + gd_id + "\t" + gd_url + "\t" + gd_id2 + "\t" + gd_url2;
    }
}
