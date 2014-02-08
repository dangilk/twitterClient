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
	
	
	@Override
	public void onStart(){
		super.onStart();
		getHomeTimeline(null);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	        	int count = adapter.getCount();
	        	if(count > 0){
	        		String minId = Long.toString(Long.valueOf(adapter.getItem(count-1).getStrId())-1);
	        		getHomeTimeline(minId);
	        	}
	        }
	        });
	}
	
	public void getHomeTimeline(String maxId){
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
