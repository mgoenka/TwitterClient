package com.mgoenka.twitterclient.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mgoenka.twitterclient.R;
import com.mgoenka.twitterclient.TweetsAdapter;
import com.mgoenka.twitterclient.models.Tweet;

public class TweetsListFragment extends Fragment {
	ListView lvTweets;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	TweetsAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_tweets_list, parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapter);

		// Attach the listener to the AdapterView onCreate
		/*lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	            // Triggered only when new data needs to be appended to the list
	            // Add whatever code is needed to append new items to your AdapterView
	        	updateTimeline(true);
	        }
        });
		
		updateTimeline(false);*/
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
