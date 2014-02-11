package com.dan.twitterclient;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.twitterclient.fragments.TweetsListFragment;
import com.dan.twitterclient.fragments.UserTimelineFragment;
import com.dan.twitterclient.models.Tweet;
import com.dan.twitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetAdapter extends ArrayAdapter<Tweet>{
	Context activity;
	
	public TweetAdapter(Context context, List<Tweet> tweets){
		super(context,0,tweets);
		activity = context;
	}
	
	@Override
	public View getView(int position,View convertView, ViewGroup parent){
		View view = convertView;
		if(view == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		final Tweet tweet = getItem(position);
		ImageView imageView = (ImageView)view.findViewById(R.id.ivProfile);
		imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(TweetsListFragment.tweetDepth > 0 ){
					return;
				}
				Intent i = new Intent(activity,ProfileActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				User u = tweet.getUser();
				i.putExtra("user", u);
				activity.startActivity(i);
			}
			
		});
		ImageLoader.getInstance().displayImage(tweet.getUser().getImage(),imageView);
		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + " <small><font color-'#777777'>@"+tweet.getUser().getScreenName()+"</font></small>" +
				" <small><font color-'#777777'>"+tweet.getRelativeTimeCreated(new Date(), activity)+"</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView bodyView = (TextView)view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		return view;
	}



}
