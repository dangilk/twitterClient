package com.dan.twitterclient.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.dan.twitterclient.TwitterClientApp;
import com.dan.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterClientApp.getRestClient().getMentions(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				getAdapter().addAll(Tweet.fromJsonArray(jsonTweets));
			}
		});
	}
}
