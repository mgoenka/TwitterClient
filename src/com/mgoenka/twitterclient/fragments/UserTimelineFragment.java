package com.mgoenka.twitterclient.fragments;

import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mgoenka.twitterclient.TwitterClientApp;
import com.mgoenka.twitterclient.models.Tweet;

import android.os.Bundle;

public class UserTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterClientApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				/*if (!more) {
					tweets.clear();
					// new Delete().from(Tweet.class).execute();
				}*/
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
		});
			//}, more, lastTweetId);
	}
}
