package com.mgoenka.twitterclient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mgoenka.twitterclient.models.Tweet;

public class TimelineActivity extends Activity {
	private final int REQUEST_CODE = 1;
	ListView lvTweets;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	TweetsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		adapter = new TweetsAdapter(this, tweets);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapter);

		// Attach the listener to the AdapterView onCreate
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	            // Triggered only when new data needs to be appended to the list
	            // Add whatever code is needed to append new items to your AdapterView
	        	updateTimeline(true);
	        }
        });
		
		updateTimeline(false);
	}
	
	private void updateTimeline(final boolean more) {
		int tweetsSize = tweets.size();
		long lastTweetId = tweetsSize > 0 ? tweets.get(tweetsSize - 1).getUserId() : 0;
		
		if (isNetworkAvailable()) {
			TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					if (!more) {
						tweets.clear();
						// new Delete().from(Tweet.class).execute();
					}
					adapter.addAll(Tweet.fromJson(jsonTweets));
				}
			}, more, lastTweetId);
		} else if (!more) {
			tweets.clear();
			List<Tweet> twt = new Select().from(Tweet.class).execute();
			ArrayList<Tweet> tweetsData = new ArrayList<Tweet>(twt);
			adapter.addAll(tweetsData);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onRefresh(MenuItem mi) {
		updateTimeline(false);
	}
	
	public void onComposeTweet(MenuItem mi) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // REQUEST_CODE is defined above
	    if (requestCode == REQUEST_CODE) {
	    	try {
				Thread.sleep(500);
		    	updateTimeline(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	}
	
	protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        
        return activeNetworkInfo != null;
    }
}
