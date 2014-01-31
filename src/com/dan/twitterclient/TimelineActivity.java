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
	
	ListView lvTweets;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	TweetAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		lvTweets = (ListView)findViewById(R.id.lvTweets);
		adapter = new TweetAdapter(getBaseContext(),tweets);
		lvTweets.setAdapter(adapter);
		
		
		// Attach the listener to the AdapterView onCreate
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
        	int count = adapter.getCount();
        	if(count > 0){
        		String minId = Long.toString(Long.valueOf(adapter.getItem(count-1).getId())-1);
        		getHomeTimeline(minId);
        	}
        }
        });
	}

	
	
	@Override
	public void onStart(){
		super.onStart();
		//adapter.clear();
		getHomeTimeline(null);
	}
	
	public void getHomeTimeline(String maxId){
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
