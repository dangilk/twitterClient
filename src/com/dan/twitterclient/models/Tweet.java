package com.dan.twitterclient.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

public class Tweet {

	private User user;
	private String body;
	private Date created;
	private String id;
	
	public Tweet(JSONObject object){
		super();
		try {
			this.body = object.getString("text");
			this.setId(object.getString("id_str"));
			String createdAt = object.getString("created_at");
			try {
				this.created = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZ yyyy",Locale.ENGLISH).parse(createdAt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.user = new User(object.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Tweet(String body){
		this.user = new User();
		this.body = body;
		this.created = new Date();
		this.id = "";
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
