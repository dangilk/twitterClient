package com.dan.twitterclient.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name="Users")
public class User extends Model{
	@Column(name="name")
	private String name = "";
	@Column(name="location")
	private String location = "";
	@Column(name="image")
	private String image = "";
	@Column(name="screenName",unique=true)
	private String screenName = "";
	public static String defaultName="";
	public static String defaultImage="";
	public static String defaultScreenName="";
	public static Integer myFollowers = 0;
	public static Integer myFollowing = 0;
	public static String myDescription = "";
	
	
	
	
	public User(JSONObject object){
		super();
		try {
			this.name = object.getString("name");
			this.location = object.getString("location");
			this.setImage(object.getString("profile_image_url"));
			this.screenName = object.getString("screen_name");
			this.save();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public User(){
		super();
		this.name = defaultName;
		this.image = defaultImage;
		this.screenName = defaultScreenName;
		this.save();
	}
	
	public static User getUser(JSONObject object){
		String sn;
		try {
			sn = object.getString("screen_name");
			User u = new Select().from(User.class).where("screenName = ? ",sn).executeSingle();
			if(u == null){
				return new User(object);
			}else{
				return u;
			}
		} catch (Exception e) {
			return new User();
		}
	}
	
	public static void setDefaults(String name,String image, String sn,String desc, Integer followers, Integer following){
		Log.e("tag","set defaults: "+name);
		User.defaultName = name;
		User.defaultImage = image;
		User.defaultScreenName = sn;
		User.myDescription = desc;
		User.myFollowers = followers;
		User.myFollowing = following;
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
