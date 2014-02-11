package com.dan.twitterclient.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.dan.twitterclient.TwitterClientApp;
import com.dan.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	String sn = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	public void setSn(String sn){
		Log.e("tag","set sn");
		this.sn = sn;
	}
	
	public void apiCall(String maxId){
		if(isNetworkAvailable()){
			Log.e("tag","profile api call");
			TwitterClientApp.getRestClient().getUserTimeline(sn,maxId,new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONArray jsonTweets){
					getAdapter().addAll(Tweet.fromJsonArray(jsonTweets));
				}
			});
		}else{
			//adapter.addAll(Tweet.recentItems(maxId));
		}
	}
}
