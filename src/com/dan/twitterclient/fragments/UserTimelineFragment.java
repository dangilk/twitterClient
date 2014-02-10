package com.dan.twitterclient.fragments;

import org.json.JSONArray;

import android.os.Bundle;

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
		this.sn = sn;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		TwitterClientApp.getRestClient().getUserTimeline(sn,new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				getAdapter().addAll(Tweet.fromJsonArray(jsonTweets));
			}
		});
	}
}
