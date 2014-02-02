package com.mgoenka.twitterclient;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mgoenka.twitterclient.controllers.TwitterClientApp;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
	}
	
	public void onCancel(View v) {
		finish();
	}
	
	public void onTweet(View v) {
		postTweet();
	}
	
	protected void postTweet() {
		String composedTweet = ((EditText) findViewById(R.id.etComposedTweet)).getText().toString();
		
		TwitterClientApp.getRestClient().postStatusUpdates(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray postTweets) {
				// Update timeline
				// TimelineActivity.updateTweets();
			}
		}, composedTweet);
		finish();
	}
}
