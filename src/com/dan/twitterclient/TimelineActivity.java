package com.dan.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.dan.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				Log.e("tag",jsonTweets.toString());
				ArrayList<Tweet> tweets = Tweet.fromJsonArray(jsonTweets);
				ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
				TweetAdapter adapter = new TweetAdapter(getBaseContext(),tweets);
				lvTweets.setAdapter(adapter);
			}
			
		
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void composeClick(MenuItem mi){
		Intent i = new Intent(this,ComposeActivity.class);
		startActivity(i);
	}

}
