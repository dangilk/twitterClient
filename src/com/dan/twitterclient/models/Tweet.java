package com.dan.twitterclient.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {

	private User user;
	private String body;
	private String createdAt;
	private String id;
	
	public Tweet(JSONObject object){
		super();
		try {
			this.body = object.getString("text");
			this.id = object.getString("id_str");
			this.createdAt = object.getString("created_at");
			this.user = new User(object.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
