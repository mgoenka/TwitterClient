package com.mgoenka.twitterclient.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mgoenka.twitterclient.EndlessScrollListener;
import com.mgoenka.twitterclient.ProfileActivity;
import com.mgoenka.twitterclient.R;
import com.mgoenka.twitterclient.TweetsAdapter;
import com.mgoenka.twitterclient.TwitterClientApp;
import com.mgoenka.twitterclient.helpers.Utilities;
import com.mgoenka.twitterclient.models.Tweet;

public abstract class TweetsListFragment extends Fragment {
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
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	            // Triggered only when new data needs to be appended to the list
	            // Add whatever code is needed to append new items to your AdapterView
	        	updateTweets(true);
	        }
        });
		
		updateTweets(false);
	}
	
	public abstract String getTweetType();
	
	public void updateTweets(final boolean more) {
		int tweetsSize = tweets.size();
		long lastTweetId = tweetsSize > 0 ? tweets.get(tweetsSize - 1).getUserId() : 0;
		String tweetType = getTweetType();
		
		if (Utilities.isNetworkAvailable(getActivity())) {
			TwitterClientApp.getRestClient().getTweets(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					if (!more) {
						tweets.clear();
						new Delete().from(Tweet.class).execute();
					}
					getAdapter().addAll(Tweet.fromJson(jsonTweets));
				}
				
				@Override
				public void handleFailureMessage(Throwable e, String responseBody) {
					if (responseBody.indexOf("\"code\":88") > 0) {
						Toast.makeText(getActivity(), getString(R.string.api_limit_exceeded), Toast.LENGTH_SHORT).show();
						displayCachedTweets();
					}
				}
			}, tweetType, more, lastTweetId, (tweetType.equals("user") ? ProfileActivity.getScreenName() : null));
		} else if (!more) {
			displayCachedTweets();
		}
	}
	
	private void displayCachedTweets() {
		tweets.clear();
		List<Tweet> twt = new Select().from(Tweet.class).execute();
		ArrayList<Tweet> tweetsData = new ArrayList<Tweet>(twt);
		getAdapter().addAll(tweetsData);
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
