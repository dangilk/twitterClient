package com.dan.twitterclient;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.twitterclient.fragments.TweetsListFragment;
import com.dan.twitterclient.fragments.UserTimelineFragment;
import com.dan.twitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		TweetsListFragment.updateDepth(1);
		Bundle data = getIntent().getExtras();
		User u = (User) data.getParcelable("user");
		getActionBar().setTitle("@"+u.getScreenName());
		UserTimelineFragment ut = (UserTimelineFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
		ut.setSn(u.getScreenName());
		
		
		
		
		
		populateProfileHeader(u.getImage(),u.getName(),u.getDescription(),u.getFollowers(),u.getFollowing());
	}

	private void populateProfileHeader(String image, String name, String description, Integer followers, Integer following) {
		ImageView iv =(ImageView) findViewById(R.id.ivProfileImage);
		TextView tv = (TextView)findViewById(R.id.tvProfileName);
		tv.setText(name);
		tv = (TextView)findViewById(R.id.tvTagline);
		tv.setText(description);
		tv = (TextView)findViewById(R.id.tvFollowers);
		tv.setText("My followers: "+followers);
		tv=(TextView)findViewById(R.id.tvFollowing);
		tv.setText("Following: "+following);
		ImageLoader.getInstance().displayImage(image, iv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
