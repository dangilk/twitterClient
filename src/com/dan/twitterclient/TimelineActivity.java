package com.dan.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.dan.twitterclient.models.Tweet;
import com.dan.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class TimelineActivity extends Activity {
	
	ListView lvTweets;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	TweetAdapter adapter;
	private final int COMPOSE_ACTIVITY = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		
		//Tweet.deleteAll();
		
		
		lvTweets = (ListView)findViewById(R.id.lvTweets);
		adapter = new TweetAdapter(getBaseContext(),tweets);
		lvTweets.setAdapter(adapter);
		
		TwitterClientApp.getRestClient().getUserCredentials(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject userInfo){
				try {
					User.setDefaults(userInfo.getString("name"), userInfo.getString("profile_image_url"), userInfo.getString("screen_name"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONArray error){
				Log.e("tag","error: "+e.getMessage());
			}
			
		});
		
		
		// Attach the listener to the AdapterView onCreate
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

	
	
	@Override
	public void onStart(){
		super.onStart();
		//adapter.clear();
		getHomeTimeline(null);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public void composeClick(MenuItem mi){
		Intent i = new Intent(this,ComposeActivity.class);
		startActivityForResult(i,COMPOSE_ACTIVITY);
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode, Intent data){
		if(resultCode == RESULT_OK && requestCode == COMPOSE_ACTIVITY){
			String body = data.getStringExtra("tweet");
			Tweet t = new Tweet(body);
			adapter.insert(t, 0);
		}
	}

}
