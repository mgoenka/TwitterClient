package com.mgoenka.twitterclient.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;

import com.activeandroid.query.Select;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mgoenka.twitterclient.TwitterClientApp;
import com.mgoenka.twitterclient.helpers.Utilities;
import com.mgoenka.twitterclient.models.Tweet;

public class HomeTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		updateTweets(false);
	}
	
	@Override
	public void updateTweets(final boolean more) {
		int tweetsSize = tweets.size();
		long lastTweetId = tweetsSize > 0 ? tweets.get(tweetsSize - 1).getUserId() : 0;
		
		if (Utilities.isNetworkAvailable()) {
			TwitterClientApp.getRestClient().getTweets(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					if (!more) {
						tweets.clear();
						// new Delete().from(Tweet.class).execute();
					}
					getAdapter().addAll(Tweet.fromJson(jsonTweets));
				}
			}, "home", more, lastTweetId);
		} else if (!more) {
			tweets.clear();
			List<Tweet> twt = new Select().from(Tweet.class).execute();
			ArrayList<Tweet> tweetsData = new ArrayList<Tweet>(twt);
			getAdapter().addAll(tweetsData);
		}
	}
	
	@Override
	public String getTweetType() {
		// Override this method in other classes
		return "home";
	}
}
