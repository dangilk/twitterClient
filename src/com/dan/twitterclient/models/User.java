package com.dan.twitterclient.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name = "";
	private String location = "";
	private String image = "";
	private String screenName = "";
	private static String defaultName="";
	private static String defaultImage="";
	private static String defaultScreenName="";
	
	public User(JSONObject object){
		super();
		try {
			this.name = object.getString("name");
			this.location = object.getString("location");
			this.setImage(object.getString("profile_image_url"));
			this.screenName = object.getString("screen_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public User(){
		this.name = defaultName;
		this.image = defaultImage;
		this.screenName = defaultScreenName;
	}
	
	public static void setDefaults(String name,String image, String sn){
		User.defaultName = name;
		User.defaultImage = image;
		User.defaultScreenName = sn;
	}
	
	public static ArrayList<User> fromJsonArray(JSONArray array){
		ArrayList<User> ret = new ArrayList<User>();
		for(int i=0;i<array.length();i++){
			try {
				ret.add(new User(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
