package com.mgoenka.twitterclient.fragments;

import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		updateTweets(false);
	}

	@Override
	public String getTweetType() {
		// Override this method in other classes
		return "home";
	}
}
