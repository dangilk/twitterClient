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

import com.dan.twitterclient.EndlessScrollListener;
import com.dan.twitterclient.R;
import com.dan.twitterclient.TweetAdapter;
import com.dan.twitterclient.models.Tweet;
import com.dan.twitterclient.models.User;

public abstract class TweetsListFragment extends Fragment{
	TweetAdapter adapter;
	ListView lvTweets;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	Listener listener = null;
	public static int tweetDepth = 0;
	
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
		
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	        	int count = adapter.getCount();
	        	if(count > 0){
	        		String minId = Long.toString(Long.valueOf(adapter.getItem(count-1).getStrId())-1);
	        		apiCall(minId);
	        	}
	        }
	        });
		return v;
	}
	
	public static void updateDepth(int i){
		tweetDepth = i;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		apiCall(null);
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
	
	abstract void apiCall(String maxId);

}
