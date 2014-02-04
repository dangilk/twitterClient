package com.dan.twitterclient.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

@Table(name="Tweets")
public class Tweet extends Model{

	@Column(name="user")
	private User user;
	@Column(name="body")
	private String body;
	@Column(name="created")
	private Date created;
	@Column(name="strId", unique=true)
	private String strId;
	
	public Tweet(){
		super();
	}
	
	public Tweet(JSONObject object){
		super();
		try {
			this.body = object.getString("text");
			this.setStrId(object.getString("id_str"));
			String createdAt = object.getString("created_at");
			try {
				this.created = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZ yyyy",Locale.ENGLISH).parse(createdAt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.user = User.getUser(object.getJSONObject("user"));
			this.save();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public Tweet(String body){
		this.user = User.getUser(null);
		this.body = body;
		this.created = new Date();
		this.strId = "";
		this.save();
	}
	
	public static ArrayList<Tweet> fromJsonArray(JSONArray array){
		ArrayList<Tweet> ret = new ArrayList<Tweet>();
		for(int i=0;i<array.length();i++){
			try {
				ret.add(new Tweet(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getRelativeTimeCreated(Date now,Context c){
		Date d;
		long time = created.getTime();
		String str =  (String) DateUtils.getRelativeDateTimeString(c, time , DateUtils.SECOND_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0);
		return str;
	}
	
	public String getStrId(){
		return strId;
	}
	
	public static List<Tweet> recentItems(String maxId) {
		if(maxId==null){
			return new Select().from(Tweet.class).orderBy("created DESC").limit("20").execute();
		}else{
			return new Select().from(Tweet.class).where("strId <= "+maxId).orderBy("created DESC").limit("20").execute();
		}
	    
	}
	
	public static void deleteAll(){
		new Delete().from(Tweet.class).execute();
	}



	public void setStrId(String id) {
		this.strId = id;
	}
}
