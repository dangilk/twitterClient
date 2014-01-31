package com.dan.twitterclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	EditText etTweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		etTweet = (EditText) findViewById(R.id.etTweet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void clickTweet(View v){
		TwitterClientApp.getRestClient().tweet(etTweet.getText().toString(),new JsonHttpResponseHandler(){
		});
	}
	

}
