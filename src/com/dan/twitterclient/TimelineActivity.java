package com.dan.twitterclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.dan.twitterclient.fragments.MentionsFragment;
import com.dan.twitterclient.fragments.TimelineFragment;
import com.dan.twitterclient.fragments.TweetsListFragment;
import com.dan.twitterclient.models.Tweet;
import com.dan.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class TimelineActivity extends FragmentActivity implements TabListener, TweetsListFragment.Listener {
	
	
	private final int COMPOSE_ACTIVITY = 0;
	TimelineFragment timelineFragment;
	MentionsFragment mentionsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		timelineFragment = new TimelineFragment();
		mentionsFragment = new MentionsFragment();
		timelineFragment.setListener(this);
		mentionsFragment.setListener(this);
		
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		setupNavigationTabs();
		
		TwitterClientApp.getRestClient().getUserCredentials(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject userInfo){
				try {
					User.setDefaults(userInfo.getString("name"), userInfo.getString("profile_image_url"), userInfo.getString("screen_name"),
							userInfo.getString("description"),userInfo.getInt("followers_count"),userInfo.getInt("friends_count"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONArray error){
				Log.e("tag","error: "+e.getMessage());
			}
			
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		TweetsListFragment.updateDepth(0);
	}

	private void setupNavigationTabs() {
		ActionBar bar = getActionBar();
		bar.setNavigationMode(bar.NAVIGATION_MODE_TABS);
		bar.setDisplayShowTitleEnabled(true);
		Tab tabHome = bar.newTab().setText("Home").setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home).setTabListener(this);
		Tab tabMentions = bar.newTab().setText("Mentions").setTag("MentionsFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
		bar.addTab(tabHome);
		bar.addTab(tabMentions);
		bar.selectTab(tabHome);
	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	
	
	public void composeClick(MenuItem mi){
		Intent i = new Intent(this,ComposeActivity.class);
		startActivityForResult(i,COMPOSE_ACTIVITY);
		ActionBar bar = getActionBar();
		bar.setSelectedNavigationItem(0);
	}

	@Override
	protected void onActivityResult(int requestCode,int resultCode, Intent data){
		if(resultCode == RESULT_OK && requestCode == COMPOSE_ACTIVITY){
			
			String body = data.getStringExtra("tweet");
			Tweet t = new Tweet(body);
			
			TweetsListFragment tf = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.frame_container);
			tf.insertTweetTop(t);
			
		}
	}






	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}





	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag().equals("HomeTimelineFragment")){
			switchToFragment(timelineFragment);
		}else{
			switchToFragment(mentionsFragment);
		}
		fts.commit();
	}
	
	public void onProfileView(MenuItem mi){
		profileClicked(User.getUser(null));
	}
	
	public void switchToFragment(Fragment newFrag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, newFrag)
                .commitAllowingStateLoss();
    }




	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}





	@Override
	public void profileClicked(User u) {
		Intent i = new Intent(this,ProfileActivity.class);
		i.putExtra("user", u);
		startActivity(i);
	}
}
