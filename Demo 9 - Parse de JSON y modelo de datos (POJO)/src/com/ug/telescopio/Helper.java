package com.ug.telescopio;

public class Helper {
	public final static String INSTAGRAM_API_KEY = "fc8041d4af1544a2939c3f5a9a1ef8cf";
	public final static String BASE_API_URL = "https://api.instagram.com/v1/";
	
	public static String getRecentUrl(String tag){
		return BASE_API_URL + "tags/" + tag + "/media/recent?client_id=" + INSTAGRAM_API_KEY; 
	}
}
