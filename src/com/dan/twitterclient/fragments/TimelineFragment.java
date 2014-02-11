package com.dan.twitterclient.fragments;

import org.json.JSONArray;

import com.dan.twitterclient.EndlessScrollListener;
import com.dan.twitterclient.TwitterClientApp;
import com.dan.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimelineFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void apiCall(String maxId){
		if(isNetworkAvailable()){
			TwitterClientApp.getRestClient().getHomeTimeline(maxId,new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONArray jsonTweets){
					Log.e("tag",jsonTweets.toString());
					adapter.addAll( Tweet.fromJsonArray(jsonTweets));
				}
				
				@Override
				public void onFailure(Throwable e, JSONArray error){
					Log.e("tag","error: "+e.getMessage());
				}
				
			});
		}else{
			adapter.addAll(Tweet.recentItems(maxId));
		}
	}
}
