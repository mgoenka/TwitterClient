package com.mgoenka.twitterclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
	}
	
	@Override
	public void onBackPressed() {
		String composedTweet = ((EditText) findViewById(R.id.etComposedTweet)).getText().toString();
		
		TwitterClientApp.getRestClient().postStatusUpdates(new JsonHttpResponseHandler() {
			public void onSuccess() {
			}
		}, composedTweet);
		finish();
	}
}
