package com.dan.twitterclient.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.dan.twitterclient.R;
import com.dan.twitterclient.TweetAdapter;
import com.dan.twitterclient.models.Tweet;
import com.dan.twitterclient.models.User;

public class TweetsListFragment extends Fragment implements OnItemClickListener{
	TweetAdapter adapter;
	ListView lvTweets;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	Listener listener = null;
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public interface Listener{
		public void profileClicked(User u);
	}

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState){
		View v = inf.inflate(R.layout.fragment_tweets_list, parent,false);
		lvTweets = (ListView)v.findViewById(R.id.lvTweets);
		adapter = new TweetAdapter(getActivity().getBaseContext(),tweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnItemClickListener(this);
		return v;
	}
	
	public void insertTweetTop(Tweet t){
		adapter.insert(t, 0);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}
	
	public TweetAdapter getAdapter(){
		return adapter;
	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(listener != null){
			Tweet t = tweets.get(position);
			listener.profileClicked(t.getUser());
		}
		
	}
}
