package com.mgoenka.twitterclient.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;

import com.activeandroid.query.Select;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mgoenka.twitterclient.TwitterClientApp;
import com.mgoenka.twitterclient.models.Tweet;

public class HomeTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		updateTimeline(false);
	}
	
	public void updateTimeline(final boolean more) {
		// int tweetsSize = tweets.size();
		// long lastTweetId = tweetsSize > 0 ? tweets.get(tweetsSize - 1).getUserId() : 0;
		
		if (isNetworkAvailable()) {
			TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					if (!more) {
						tweets.clear();
						// new Delete().from(Tweet.class).execute();
					}
					getAdapter().addAll(Tweet.fromJson(jsonTweets));
				}
			});
			// }, more, lastTweetId);
		} else if (!more) {
			tweets.clear();
			List<Tweet> twt = new Select().from(Tweet.class).execute();
			ArrayList<Tweet> tweetsData = new ArrayList<Tweet>(twt);
			getAdapter().addAll(tweetsData);
		}
	}
	
	protected static boolean isNetworkAvailable() {
        //ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return true;
        //return activeNetworkInfo != null;
    }
}
