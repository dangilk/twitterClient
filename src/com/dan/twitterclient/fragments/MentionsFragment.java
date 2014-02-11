package com.dan.twitterclient.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.dan.twitterclient.TwitterClientApp;
import com.dan.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsFragment extends TweetsListFragment {

	
	
	public void apiCall(String maxId){
		if(isNetworkAvailable()){
			TwitterClientApp.getRestClient().getMentions(maxId,new JsonHttpResponseHandler(){
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
