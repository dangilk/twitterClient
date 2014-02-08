package com.dan.twitterclient;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.twitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().setTitle("@"+User.defaultScreenName);
		populateProfileHeader();
	}

	private void populateProfileHeader() {
		ImageView iv =(ImageView) findViewById(R.id.ivProfileImage);
		TextView tv = (TextView)findViewById(R.id.tvProfileName);
		tv.setText(User.defaultName);
		tv = (TextView)findViewById(R.id.tvTagline);
		tv.setText(User.myDescription);
		tv = (TextView)findViewById(R.id.tvFollowers);
		tv.setText("My followers: "+User.myFollowers);
		tv=(TextView)findViewById(R.id.tvFollowing);
		tv.setText("Following: "+User.myFollowing);
		ImageLoader.getInstance().displayImage(User.defaultImage, iv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
