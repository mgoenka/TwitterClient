package com.mgoenka.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

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
		long lastTweetId = tweetsSize > 0 ? tweets.get(tweetsSize - 1).getId() : 0;
		
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				if (!more) {
					tweets.clear();
				}
				adapter.addAll(Tweet.fromJson(jsonTweets));
			}
		}, more, lastTweetId);
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
}
